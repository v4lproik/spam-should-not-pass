package net.v4lproik.spamshouldnotpass.platform.dao.repositories;

import com.querydsl.jpa.hibernate.HibernateQuery;
import net.v4lproik.spamshouldnotpass.platform.dao.api.ContextDao;
import net.v4lproik.spamshouldnotpass.platform.models.entities.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Repository
public class ContextRepository implements ContextDao {

    private static final Logger log = LoggerFactory.getLogger(ContextRepository.class);

    public final SessionFactory sessionFactory;

    // Tables
    public final QContext qcontext = QContext.context;
    public final QRule qrule = QRule.rule1;
    public final QRuleInContext qRuleInContext = QRuleInContext.ruleInContext;

    @Autowired
    public ContextRepository(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Context> listByUserId(UUID userId) {
        Transaction tx = currentSession().beginTransaction();

        HibernateQuery<?> query = new HibernateQuery<Void>(currentSession());

        List<Context> contexts = query.from(qcontext)
                .select(qcontext)
                .where(qcontext.userId.eq(userId))
                .fetch();

        currentSession().flush();
        tx.commit();

        return contexts;
    }

    @Override
    public UUID save(Context contextToSave) {
        Transaction tx = currentSession().beginTransaction();

        UUID uuid = (UUID) currentSession().save(contextToSave);

        currentSession().flush();
        tx.commit();

        return uuid;
    }

    @Override
    public void addRule(RuleInContext ruleInContext) {
        Transaction tx = currentSession().beginTransaction();

        currentSession().save(ruleInContext);

        currentSession().flush();
        tx.commit();
    }

    @Override
    public void delete(UUID id) {
        Transaction tx = currentSession().beginTransaction();

        Context toDelete = new Context();
        toDelete.setId(id);

        currentSession().delete(toDelete);

        currentSession().flush();
        tx.commit();
    }

    @Override
    public void update(Context context) {
        Transaction tx = currentSession().beginTransaction();

        currentSession().update(context);

        currentSession().flush();
        tx.commit();
    }

    @Override
    public Context findById(UUID id) {
        Transaction tx = currentSession().beginTransaction();

        HibernateQuery<?> query = new HibernateQuery<Void>(currentSession());

        Context context = query.from(qcontext)
                .select(qcontext)
                .where(qcontext.id.eq(id))
                .fetchOne();

        currentSession().flush();
        tx.commit();

        return context;
    }

    @Override
    public Context findByIdWithRules(UUID id) {
        Transaction tx = currentSession().beginTransaction();

        HibernateQuery<?> query = new HibernateQuery<Void>(currentSession());

        Context context = query.from(qcontext).from()
                .select(qcontext)
                .where(qcontext.id.eq(id))
                .fetchOne();

        if (context != null) {
            Hibernate.initialize(context.getRules());
        }

        currentSession().flush();
        tx.commit();

        return context;
    }

    @Override
    public Context findByName(String name) {
        Transaction tx = currentSession().beginTransaction();

        HibernateQuery<?> query = new HibernateQuery<Void>(currentSession());

        Context context = query.from(qcontext)
                .select(qcontext)
                .where(qcontext.name.eq(name))
                .fetchFirst();

        currentSession().flush();
        tx.commit();

        return context;
    }

    @Override
    public Context findByNameWithRules(String name) {
        Transaction tx = currentSession().beginTransaction();

        HibernateQuery<?> query = new HibernateQuery<Void>(currentSession());

        Context context = query.from(qcontext)
                .select(qcontext)
                .where(qcontext.name.eq(name))
                .fetchFirst();

        if (context != null) {
            Hibernate.initialize(context.getRules());
        }

        currentSession().flush();
        tx.commit();

        return context;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }
}
