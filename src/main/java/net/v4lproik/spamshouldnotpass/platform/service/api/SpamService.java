package net.v4lproik.spamshouldnotpass.platform.service.api;

import net.v4lproik.spamshouldnotpass.platform.dao.api.UserDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class SpamService {

    private static Logger log = Logger.getLogger(SpamService.class);

    private final UserDao userDao;

    private final PasswordService passwordService;

    @Autowired
    public SpamService(final UserDao userDao, final PasswordService passwordService) {
        this.userDao = userDao;
        this.passwordService = passwordService;
    }
}
