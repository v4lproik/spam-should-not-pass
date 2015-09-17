package net.v4lproik.googlanime.dao.repositories;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Repository
public abstract class AbstractRepository<E, I extends Serializable> {

    private final Class<E> klazz;

    private final SessionFactory sessionFactoryConfig;

    public AbstractRepository(final Class<E> klazz, final SessionFactory sessionFactory) {
        this.klazz = klazz;
        this.klazz.getName();

        this.sessionFactoryConfig = sessionFactory;
    }

    public List<E> list() {
        return currentSession().createCriteria(klazz).list();
    }

    public Long save(Object entity) {
        Transaction tx=currentSession().beginTransaction();

        Object idSave = currentSession().save(entity);

        currentSession().flush();
        tx.commit();

        return new Long(String.valueOf(idSave));
    }

    public Object merge(Object entity) {
        Transaction tx=currentSession().beginTransaction();

        Object idSave = currentSession().merge(entity);

        currentSession().flush();
        tx.commit();

        return idSave;
    }

    public void saveOrUpdate(Object entity) {
        Transaction tx=currentSession().beginTransaction();

        currentSession().saveOrUpdate(entity);

        currentSession().flush();
        tx.commit();
    }

    public void update(Object entity) {
        Transaction tx=currentSession().beginTransaction();

        currentSession().update(entity);

        currentSession().flush();
        tx.commit();
    }

    public Object getById(I id) {
        Transaction tx=currentSession().beginTransaction();

        Object E = currentSession().get(klazz, id);

        currentSession().flush();
        tx.commit();

        return E;
    }

    public void delete(E entity) {
        Transaction tx=currentSession().beginTransaction();

        currentSession().delete(entity);

        currentSession().flush();
        tx.commit();
    }

    public void deleteById(Long id) {
        Transaction tx=currentSession().beginTransaction();

        try{
            Object E =  currentSession().load(klazz, id);

            currentSession().delete(E);

            currentSession().flush();
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            throw ex;
        }
    }
    protected Object getBySimpleCondition(Class<?> type, String columnName, String columnValue){
        Transaction tx=currentSession().beginTransaction();

        Criteria criteria =  currentSession().createCriteria(type);
        if ( columnValue != null) criteria.add(Restrictions.eq(columnName, columnValue));
        Object obj = criteria.uniqueResult();

        currentSession().flush();
        tx.commit();

        return obj;
    }

    protected Object getBySimpleCondition(Class<?> type, Map<String, String> conditions){
        Object obj = null;

        Transaction tx=currentSession().beginTransaction();

        Criteria criteria =  currentSession().createCriteria(type);

        for (Map.Entry<String, String> entry : conditions.entrySet())
        {
            if ( entry.getValue() != null)
                obj = criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
        }

        obj = criteria.uniqueResult();

        currentSession().flush();
        tx.commit();

        return obj;
    }

    protected Object getBySimpleConditionObject(Class<?> type, Map<String, Object> conditions){
        Object obj = null;

        Transaction tx=currentSession().beginTransaction();

        Criteria criteria =  currentSession().createCriteria(type);

        for (Map.Entry<String, Object> entry : conditions.entrySet())
        {
            if ( entry.getValue() != null) criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
        }

        obj = criteria.uniqueResult();

        currentSession().flush();
        tx.commit();

        return obj;
    }

    protected Session currentSession() {
        return sessionFactoryConfig.getCurrentSession();
    }
}