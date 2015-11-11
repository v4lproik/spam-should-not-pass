package net.v4lproik.spamshouldnotpass.platform.dao.api;

import net.v4lproik.spamshouldnotpass.platform.models.entities.User;

import java.util.UUID;

public interface UserDao {
    User findById(UUID id);
    User findByEmail(String email);

    UUID save(User user);

    void delete(UUID id);
    void deleteByEmail(String email);
}
