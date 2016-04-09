package net.v4lproik.spamshouldnotpass.platform.repositories;

import com.querydsl.jpa.hibernate.HibernateQuery;
import net.v4lproik.spamshouldnotpass.platform.models.entities.QRule;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Rule;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RulesRepository extends net.v4lproik.spamshouldnotpass.platform.repositories.Repository<Rule> {

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
    public Optional<UUID> save(Rule ruleToSave) {
        Transaction tx = currentSession().beginTransaction();

        UUID uuid = (UUID) currentSession().save(ruleToSave);

        currentSession().flush();
        tx.commit();

        return Optional.ofNullable(uuid);
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
    public void update(Rule rule) {
        Transaction tx = currentSession().beginTransaction();

        currentSession().update(rule);

        currentSession().flush();
        tx.commit();
    }

    @Override
    public Optional<Rule> findById(UUID id) {
        Transaction tx = currentSession().beginTransaction();

        HibernateQuery<?> query = new HibernateQuery<Void>(currentSession());

        Rule rule = query.from(qrule)
                .select(qrule)
                .where(qrule.id.eq(id))
                .fetchFirst();

        currentSession().flush();
        tx.commit();

        return Optional.ofNullable(rule);
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }
}
