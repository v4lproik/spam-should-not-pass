package net.v4lproik.spamshouldnotpass.platform.dao.repositories;

import com.querydsl.jpa.hibernate.HibernateDeleteClause;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateUpdateClause;
import net.v4lproik.spamshouldnotpass.platform.models.entities.QUser;
import net.v4lproik.spamshouldnotpass.platform.models.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    public final SessionFactory sessionFactory;

    // Tables
    public final QUser quser = QUser.user;

    @Autowired
    public UserRepository(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UUID save(User user) {
        Transaction tx = currentSession().beginTransaction();

        UUID uuid = (UUID) currentSession().save(user);

        currentSession().flush();
        tx.commit();

        return uuid;
    }

    public void saveApiKey(UUID userId, String apiKey) {
        Transaction tx = currentSession().beginTransaction();

        new HibernateUpdateClause(currentSession(), quser)
                .set(quser.apiKey, apiKey)
                .where(quser.id.eq(userId))
                .execute();

        currentSession().flush();
        tx.commit();
    }

    public void delete(UUID id) {
        Transaction tx = currentSession().beginTransaction();

        User toDelete = new User();
        toDelete.setId(id);

        currentSession().delete(toDelete);

        currentSession().flush();
        tx.commit();
    }

    public void deleteByEmail(String email) {
        Transaction tx = currentSession().beginTransaction();

        new HibernateDeleteClause(currentSession(), quser).where(quser.email.eq(email));

        currentSession().flush();
        tx.commit();
    }

    public User findById(UUID id) {
        Transaction tx = currentSession().beginTransaction();

        HibernateQuery<?> query = new HibernateQuery<Void>(currentSession());

        User user = query.from(quser)
                .select(quser)
                .where(quser.id.eq(id))
                .fetchFirst();

        currentSession().flush();
        tx.commit();

        return user;
    }

    public User findByApiKey(String token) {
        Transaction tx = currentSession().beginTransaction();

        HibernateQuery<?> query = new HibernateQuery<Void>(currentSession());

        User user = query.from(quser)
                .select(quser)
                .where(quser.apiKey.eq(token))
                .fetchFirst();

        currentSession().flush();
        tx.commit();

        return user;
    }

    public User findByEmail(String email) {
        Transaction tx = currentSession().beginTransaction();

        HibernateQuery<?> query = new HibernateQuery<Void>(currentSession());

        User user =  query.from(quser)
                .select(quser)
                .where(quser.email.eq(email))
                .fetchFirst();

        currentSession().flush();
        tx.commit();

        return user;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }
}
