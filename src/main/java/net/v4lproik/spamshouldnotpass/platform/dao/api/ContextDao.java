package net.v4lproik.spamshouldnotpass.platform.dao.api;

import net.v4lproik.spamshouldnotpass.platform.models.entities.Context;
import net.v4lproik.spamshouldnotpass.platform.models.entities.RuleInContext;

import java.util.List;
import java.util.UUID;

public interface ContextDao {
    Context findById(UUID id);
    Context findByIdWithRules(UUID id);
    Context findByName(String name);
    Context findByNameWithRules(String name);
    UUID save(Context rule);
    void update(Context rule);
    void delete(UUID id);
    List<Context> listByUserId(UUID userId);

    void addRule(RuleInContext ruleInContext);
}
