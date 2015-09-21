package net.v4lproik.googlanime.dao.repositories;

import org.springframework.session.Session;
import org.springframework.session.SessionRepository;

public class CacheSessionRepository<S extends Session> {

    public static final String MEMBER_KEY = "member_v1";

    private final SessionRepository sessionRepository;

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
