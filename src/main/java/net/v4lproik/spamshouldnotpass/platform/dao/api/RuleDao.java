package net.v4lproik.spamshouldnotpass.platform.dao.api;

import net.v4lproik.spamshouldnotpass.platform.models.RuleType;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Rule;

import java.util.List;
import java.util.UUID;

public interface RuleDao {
    Rule findById(UUID id);

    UUID save(Rule rule);

    void update(Rule rule);

    void delete(UUID id);

    List<Rule> list();
    List<Rule> listByUserId(UUID userId);
    List<Rule> listByUserIdAndType(UUID userId, RuleType type);
}
