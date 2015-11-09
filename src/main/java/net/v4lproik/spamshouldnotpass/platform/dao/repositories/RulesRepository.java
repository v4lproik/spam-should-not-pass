package net.v4lproik.spamshouldnotpass.platform.dao.repositories;

import com.querydsl.jpa.hibernate.HibernateQuery;
import net.v4lproik.spamshouldnotpass.platform.dao.api.RuleDao;
import net.v4lproik.spamshouldnotpass.platform.models.RuleType;
import net.v4lproik.spamshouldnotpass.platform.service.api.entities.QRule;
import net.v4lproik.spamshouldnotpass.platform.service.api.entities.Rule;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class RulesRepository implements RuleDao {

    private static final Logger log = LoggerFactory.getLogger(RulesRepository.class);

    public final SessionFactory sessionFactory;

    // Tables
    public final QRule qrule = QRule.rule1;

    @Autowired
    public RulesRepository(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Rule> list() {
        Transaction tx = currentSession().beginTransaction();

        HibernateQuery<?> query = new HibernateQuery<Void>(currentSession());

        List<Rule> rules = query.from(qrule)
                .select(qrule)
                .fetch();

        currentSession().flush();
        tx.commit();

        return rules;
    }

    @Override
    public List<Rule> listByUserId(UUID userId) {
        Transaction tx = currentSession().beginTransaction();

        HibernateQuery<?> query = new HibernateQuery<Void>(currentSession());

        List<Rule> rules = query.from(qrule)
                .select(qrule)
                .where(qrule.userId.eq(userId))
                .fetch();

        currentSession().flush();
        tx.commit();

        return rules;
    }

    @Override
    public List<Rule> listByUserIdAndType(UUID userId, RuleType type) {
        Transaction tx = currentSession().beginTransaction();

        HibernateQuery<?> query = new HibernateQuery<Void>(currentSession());

        List<Rule> rules = query.from(qrule)
                .select(qrule)
                .where(qrule.userId.eq(userId)
                        .and(qrule.type.eq(type)))
                .fetch();

        currentSession().flush();
        tx.commit();

        return rules;
    }

    @Override
    public UUID save(Rule ruleToSave) {
        Transaction tx = currentSession().beginTransaction();

        UUID uuid = (UUID) currentSession().save(ruleToSave);

        currentSession().flush();
        tx.commit();

        return uuid;
    }

    @Override
    public void delete(UUID id) {
        Transaction tx = currentSession().beginTransaction();

        Rule toDelete = new Rule();
        toDelete.setId(id);

        currentSession().delete(toDelete);

        currentSession().flush();
        tx.commit();
    }

    @Override
    public Rule findById(UUID id) {
        Transaction tx = currentSession().beginTransaction();

        HibernateQuery<?> query = new HibernateQuery<Void>(currentSession());

        Rule rule = query.from(qrule)
                .select(qrule)
                .where(qrule.id.eq(id))
                .fetchFirst();

        currentSession().flush();
        tx.commit();

        return rule;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }
}
