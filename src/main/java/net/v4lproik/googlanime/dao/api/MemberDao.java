package net.v4lproik.googlanime.dao.api;

import net.v4lproik.googlanime.service.api.entities.Member;

public interface MemberDao {
    Member findById(Integer id);
    Member find(String email);

    Member save(String email, String password);
}
