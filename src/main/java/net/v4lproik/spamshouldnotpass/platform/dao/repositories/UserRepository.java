package net.v4lproik.spamshouldnotpass.platform.dao.repositories;

import com.querydsl.jpa.hibernate.HibernateQuery;
import net.v4lproik.spamshouldnotpass.platform.dao.api.UserDao;
import net.v4lproik.spamshouldnotpass.platform.service.api.entities.QUser;
import net.v4lproik.spamshouldnotpass.platform.service.api.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRepository implements UserDao {

    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    public final SessionFactory sessionFactory;

    // Tables
    public final QUser quser = QUser.user;

    @Autowired
    public UserRepository(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User save(User user) {
        Transaction tx = currentSession().beginTransaction();

        currentSession().save(user);

        currentSession().flush();
        tx.commit();

        return user;
    }

    @Override
    public void delete(UUID id) {
        Transaction tx = currentSession().beginTransaction();

        User toDelete = new User();
        toDelete.setId(id);

        currentSession().delete(toDelete);

        currentSession().flush();
        tx.commit();
    }

    @Override
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

    @Override
    public User findByEmail(String email) {
        HibernateQuery<?> query = new HibernateQuery<Void>(currentSession());

        return query.select(quser).where(quser.email.eq(email)).fetchFirst();
    }

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

}
