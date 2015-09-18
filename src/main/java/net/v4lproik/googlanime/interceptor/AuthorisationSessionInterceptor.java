package net.v4lproik.googlanime.interceptor;

import net.v4lproik.googlanime.annotation.PrivateAccess;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.session.SessionRepository;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

public class AuthorisationSessionInterceptor extends HandlerInterceptorAdapter {

    static Logger log = Logger.getLogger(AuthorisationSessionInterceptor.class.getName());

    @Autowired
    SessionRepository repository;

    public AuthorisationSessionInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {

        log.debug("[AuthorisationSessionInterceptor] job starts");

        Method methodToTest = ((HandlerMethod) handler).getMethod();

        if (AnnotationUtils.getAnnotation(methodToTest, PrivateAccess.class) == null) {
            log.debug(String.format("[AuthorisationSessionInterceptor] %s is public access", methodToTest.toGenericString()));
            return true;
        }

        log.debug(String.format("[AuthorisationSessionInterceptor] %s is private access", methodToTest.toGenericString()));
        final HttpSession session = req.getSession();

        if (session == null){
            return false;
        }

        if (repository.getSession(session.getId()) == null){
            return false;
        }

        log.debug("[AuthorisationSessionInterceptor] job finished");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
