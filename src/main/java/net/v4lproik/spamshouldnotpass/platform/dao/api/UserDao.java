package net.v4lproik.spamshouldnotpass.platform.dao.api;

import net.v4lproik.spamshouldnotpass.platform.service.api.entities.User;

import java.util.UUID;

public interface UserDao {
    User findById(UUID id);
    User findByEmail(String email);

    User save(User user);

    void delete(UUID id);
}
