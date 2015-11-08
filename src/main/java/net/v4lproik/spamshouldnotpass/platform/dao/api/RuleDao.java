package net.v4lproik.spamshouldnotpass.platform.dao.api;

import net.v4lproik.spamshouldnotpass.platform.service.api.entities.Rule;

import java.util.List;
import java.util.UUID;

public interface RuleDao {
    Rule findById(UUID id);

    UUID save(Rule user);

    void delete(UUID id);

    List<Rule> list();
    List<Rule> listByUserId(UUID userId);
}
