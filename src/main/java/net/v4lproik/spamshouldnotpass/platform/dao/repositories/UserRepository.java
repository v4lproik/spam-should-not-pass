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
    public final QUser user = QUser.user;

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

        currentSession().delete(id.toString(), User.class);

        currentSession().flush();
        tx.commit();
    }

    @Override
    public User findById(String id) {
        HibernateQuery<?> query = new HibernateQuery<Void>(currentSession());

        return query.select(user).where(user.id.eq(id)).fetchFirst();
    }

    @Override
    public User findByEmail(String email) {
        HibernateQuery<?> query = new HibernateQuery<Void>(currentSession());

        return query.select(user).where(user.email.eq(email)).fetchFirst();
    }

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

}
