package net.v4lproik.googlanime.service.api;

import net.v4lproik.googlanime.dao.api.MemberDao;
import net.v4lproik.googlanime.service.api.entities.Member;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class UserService {

    private static Logger log = Logger.getLogger(UserService.class);

    private final MemberDao memberDao;

    private final PasswordService passwordService;

    @Autowired
    public UserService(final MemberDao memberDao, final PasswordService passwordService) {
        this.memberDao = memberDao;
        this.passwordService = passwordService;
    }

    public Member authenticate(String email, String password){
        Member member = null;

        if (email == null || email.isEmpty()){
            log.debug("[UserService] The email cannot be empty or null");
            return member;
        }

        // password policy
        if (password == null || password.isEmpty()){
            log.debug("[UserService] The password cannot be empty or null");
            return member;
        }

        member = memberDao.find(email);

        if (member == null){
            return member;
        }

        boolean auth = false;
        try {
            auth = passwordService.validatePassword(password, member.getPassword());
        } catch (NoSuchAlgorithmException e) {
            log.debug("[UserService] Error generating password to check if user exist in database", e);
            return null;
        } catch (InvalidKeySpecException e) {
            log.debug("[UserService] Error generating password to check if user exist in database", e);
            return null;
        }

        if (auth){
            return member;
        }

        return null;
    }

    public Member save(String email, String password) {

        if (email == null || email.isEmpty()){
            log.debug("[UserService] The email cannot be empty or null");
            return null;
        }

        // password policy
        if (password == null || password.isEmpty()){
            log.debug("[UserService] The password cannot be empty or null");
            return null;
        }

        String passwordGenerated = null;
        try {
            passwordGenerated = passwordService.generateHash(password);
        } catch (NoSuchAlgorithmException e) {
            log.debug("[UserService] Error generating password for new user", e);
            return null;
        } catch (InvalidKeySpecException e) {
            log.debug("[UserService] Error generating password for new user", e);
            return null;
        }

        Member created = null;
        try{
            created = memberDao.save(email, passwordGenerated);
        }catch (Exception e){
            e.printStackTrace();
        }

        return created;
    }

    @Transactional(readOnly = true)
    public Member findById(Integer id) {
        return memberDao.findById(id);
    }

    @Transactional(readOnly = true)
    public Member findByLogin(String email) {
        return null;
    }
}
