package net.v4lproik.spamshouldnotpass.spring.interceptor;

import net.v4lproik.spamshouldnotpass.spring.annotation.AdminAccess;
import net.v4lproik.spamshouldnotpass.spring.annotation.UserAccess;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.CacheSessionRepository;
import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;
import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
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
    CacheSessionRepository repository;

    public AuthorisationSessionInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {

        log.debug("[AuthorisationSessionInterceptor] job starts from url " + req.getPathInfo());

        final Method methodToTest = ((HandlerMethod) handler).getMethod();
        final String methodName = methodToTest.toGenericString() == null ? "" : methodToTest.toGenericString();

        //============== PUBLIC ACCESS ===============//

        if (AnnotationUtils.getAnnotation(methodToTest, UserAccess.class) == null && AnnotationUtils.getAnnotation(methodToTest, AdminAccess.class) == null) {
            log.debug(String.format("[AuthorisationSessionInterceptor] %s is public access", methodName));
            return true;
        }


        //============== PRIVATE ACCESS ===============//

        log.debug(String.format("[AuthorisationSessionInterceptor] %s is private access", methodName));
        final HttpSession session = req.getSession();

        if (session == null){
            log.debug(String.format("[AuthorisationSessionInterceptor] job finished : No x-auth-token found"));
            return false;
        }

        if (repository.getSession(session.getId()) == null){
            log.debug(String.format("[AuthorisationSessionInterceptor] job finished : No match for session id %s", session.getId()));
            return false;
        }

        req.setAttribute(CacheSessionRepository.MEMBER_KEY, session.getAttribute(CacheSessionRepository.MEMBER_KEY));

        if (req.getPathInfo().matches("/user/auth.+")){
            log.debug(String.format("[AuthorisationSessionInterceptor] job finished : User logged and try to access authentication page"));
            res.sendRedirect("/");
            return false;
        }

        if (AnnotationUtils.getAnnotation(methodToTest, AdminAccess.class) != null) {
            log.debug(String.format("[AuthorisationSessionInterceptor] %s is admin access", methodName));

            MemberPermission permission = ((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getPermission();
            if (permission.equals(MemberPermission.ADMIN)){
                log.debug(String.format("[AuthorisationSessionInterceptor] job finished"));
                return true;
            }

            log.debug(String.format("[AuthorisationSessionInterceptor] job finished : User %s with session id %s is not ADMIN permission", permission, session.getId()));
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
