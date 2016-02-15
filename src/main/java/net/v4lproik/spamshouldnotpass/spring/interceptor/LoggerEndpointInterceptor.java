package net.v4lproik.spamshouldnotpass.spring.interceptor;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggerEndpointInterceptor extends HandlerInterceptorAdapter {

    static Logger log = Logger.getLogger(LoggerEndpointInterceptor.class.getName());

    public LoggerEndpointInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        final long startTime = System.currentTimeMillis();
        req.setAttribute("startTime", startTime);

        log.info(String.format("[LoggerEndpointInterceptor] Start url < %s >, startTime < %d >", req.getRequestURL().toString(), startTime));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        final long startTime = (Long) req.getAttribute("startTime");
        final long endTime = System.currentTimeMillis();

        log.info(String.format("[LoggerEndpointInterceptor] End url < %s >, endTime < %d >, timeTaken < %dms >", req.getRequestURL().toString(), endTime, endTime - startTime));

        super.postHandle(req, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(req, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
