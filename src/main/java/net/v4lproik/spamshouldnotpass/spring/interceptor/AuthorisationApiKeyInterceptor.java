package net.v4lproik.spamshouldnotpass.spring.interceptor;

import net.v4lproik.spamshouldnotpass.platform.dao.repositories.UserRepository;
import net.v4lproik.spamshouldnotpass.platform.models.entities.User;
import net.v4lproik.spamshouldnotpass.spring.annotation.ApiAccess;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthorisationApiKeyInterceptor extends HandlerInterceptorAdapter {

    static Logger log = Logger.getLogger(AuthorisationApiKeyInterceptor.class.getName());

    private final UserRepository userRepository;

    @Autowired
    public AuthorisationApiKeyInterceptor(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {

        final Method methodToTest = ((HandlerMethod) handler).getMethod();

        //============== API ACCESS ===============//

        if (AnnotationUtils.getAnnotation(methodToTest, ApiAccess.class) != null) {
            final String token = req.getHeader("x-auth-token");

            if (token == null){
                log.error("[AuthorisationApiKeyInterceptor] job finished : No x-auth-token found");
                res.sendError(401);
                return false;
            }

            final User user = userRepository.findByApiKey(token);

            if (user == null){
                log.error(String.format("[AuthorisationSessionInterceptor] job finished : No match for api key %s", token));
                res.sendError(403);
                return false;
            }

            req.setAttribute("userId", user.getId());
            req.setAttribute("userCorporation", user.getCorporation());

            return true;
        }

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
