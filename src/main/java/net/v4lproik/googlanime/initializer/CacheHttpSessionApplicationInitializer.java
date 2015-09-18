package net.v4lproik.googlanime.initializer;

import net.v4lproik.googlanime.client.redis.ConfigRedis;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class CacheHttpSessionApplicationInitializer
        extends AbstractHttpSessionApplicationInitializer {

    public CacheHttpSessionApplicationInitializer() {
        super(ConfigRedis.class);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException
    {
        beforeSessionRepositoryFilter(servletContext);
        afterSessionRepositoryFilter(servletContext);
    }
}