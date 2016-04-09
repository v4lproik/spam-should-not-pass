package net.v4lproik.spamshouldnotpass.platform.repositories;

import com.querydsl.jpa.hibernate.HibernateQuery;
import net.v4lproik.spamshouldnotpass.platform.models.SchemeType;
import net.v4lproik.spamshouldnotpass.platform.models.entities.QScheme;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Scheme;
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
public class SchemesRepository extends net.v4lproik.spamshouldnotpass.platform.repositories.Repository<Scheme> {

    private static final Logger log = LoggerFactory.getLogger(SchemesRepository.class);

    public final SessionFactory sessionFactory;

    // Tables
    public final QScheme qscheme = QScheme.scheme;

    @Autowired
    public SchemesRepository(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Scheme> list() {
        Transaction tx = currentSession().beginTransaction();

        HibernateQuery<?> query = new HibernateQuery<Void>(currentSession());

        List<Scheme> schemes = query.from(qscheme)
                .select(qscheme)
                .fetch();

        currentSession().flush();
        tx.commit();

        return schemes;
    }

    public List<Scheme> listByUserId(UUID userId) {
        Transaction tx = currentSession().beginTransaction();

        HibernateQuery<?> query = new HibernateQuery<Void>(currentSession());

        List<Scheme> schemes = query.from(qscheme)
                .select(qscheme)
                .where(qscheme.userId.eq(userId))
                .fetch();

        currentSession().flush();
        tx.commit();

        return schemes;
    }

    public Scheme listByUserIdAndType(UUID userId, SchemeType type) {
        Transaction tx = currentSession().beginTransaction();

        HibernateQuery<?> query = new HibernateQuery<Void>(currentSession());

        Scheme scheme = query.from(qscheme)
                .select(qscheme)
                .where(qscheme.userId.eq(userId)
                        .and(qscheme.type.eq(type)))
                .fetchOne();

        currentSession().flush();
        tx.commit();

        return scheme;
    }

    @Override
    public Optional<UUID> save(Scheme schemeToSave) {
        Transaction tx = currentSession().beginTransaction();

        UUID uuid = (UUID) currentSession().save(schemeToSave);

        currentSession().flush();
        tx.commit();

        return Optional.ofNullable(uuid);
    }

    @Override
    public void update(Scheme schemeToSave) {
        Transaction tx = currentSession().beginTransaction();

        currentSession().update(schemeToSave);

        currentSession().flush();
        tx.commit();
    }

    @Override
    public void delete(UUID id) {
        Transaction tx = currentSession().beginTransaction();

        Scheme toDelete = new Scheme();
        toDelete.setId(id);

        currentSession().delete(toDelete);

        currentSession().flush();
        tx.commit();
    }

    public Optional<Scheme> findById(UUID id) {
        Transaction tx = currentSession().beginTransaction();

        HibernateQuery<?> query = new HibernateQuery<Void>(currentSession());

        Scheme scheme = query.from(qscheme)
                .select(qscheme)
                .where(qscheme.id.eq(id))
                .fetchFirst();

        currentSession().flush();
        tx.commit();

        return Optional.ofNullable(scheme);
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }
}
