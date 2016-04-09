package net.v4lproik.spamshouldnotpass.platform.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CacheSessionRepository<S extends Session> {

    public static final String MEMBER_KEY = "member_v1";

    private final SessionRepository sessionRepository;

    @Autowired
    public CacheSessionRepository(final SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public S createSession(){
        return (S) sessionRepository.createSession();
    }

    public void save(S session){
        sessionRepository.save(session);
    }

    public S getSession(String session){
        return (S) sessionRepository.getSession(session);
    }

    public void delete(String session){
        sessionRepository.delete(session);
    }
}
