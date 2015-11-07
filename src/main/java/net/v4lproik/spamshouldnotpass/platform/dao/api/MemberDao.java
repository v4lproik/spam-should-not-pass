package net.v4lproik.spamshouldnotpass.platform.dao.api;

import net.v4lproik.spamshouldnotpass.platform.service.api.entities.Member;

public interface MemberDao {
    Member findById(Long id);
    Member find(String email);

    Member save(Member member);

    void delete(Long id);
}
