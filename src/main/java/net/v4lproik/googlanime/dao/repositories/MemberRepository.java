package net.v4lproik.googlanime.dao.repositories;

import net.v4lproik.googlanime.dao.api.MemberDao;
import net.v4lproik.googlanime.service.api.entities.Member;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository extends AbstractRepository implements MemberDao {

    private static final Logger log = LoggerFactory.getLogger(MemberRepository.class);

    public final SessionFactory sessionFactory;

    @Autowired
    public MemberRepository(final SessionFactory sessionFactory) {
        super(Member.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Member save(String email, String password) {
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);

        Transaction tx = currentSession().beginTransaction();

        Object idSave = currentSession().save(member);

        currentSession().flush();
        tx.commit();

        return new Member(new Long(idSave.toString()));
    }

    @Override
    public Member findById(Integer id) {
        Transaction tx=currentSession().beginTransaction();
        Member member = (Member) sessionFactory.getCurrentSession().get(Member.class, id);
        tx.commit();

        return member;
    }

    @Override
    public Member find(String email) {
        return (Member) getBySimpleCondition(Member.class, "email", email);
    }
}
