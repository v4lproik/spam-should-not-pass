package net.v4lproik.spamshouldnotpass.platform.service;

import net.v4lproik.spamshouldnotpass.platform.dao.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpamService {

    private static Logger log = Logger.getLogger(SpamService.class);

    private final UserRepository userDao;
    private final PasswordService passwordService;

    @Autowired
    public SpamService(final UserRepository userDao, final PasswordService passwordService) {
        this.userDao = userDao;
        this.passwordService = passwordService;
    }
}
