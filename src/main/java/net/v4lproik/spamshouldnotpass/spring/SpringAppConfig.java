package net.v4lproik.spamshouldnotpass.spring;

import net.v4lproik.spamshouldnotpass.platform.dao.api.MemberDao;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.CacheSessionRepository;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.MemberRepository;
import net.v4lproik.spamshouldnotpass.platform.service.api.PasswordService;
import net.v4lproik.spamshouldnotpass.platform.service.api.UserService;
import net.v4lproik.spamshouldnotpass.spring.interceptor.AuthorisationSessionInterceptor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.SessionRepository;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan("net.v4lproik.spamshouldnotpass")
@EnableWebMvc
public class SpringAppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    SessionRepository sessionRepository;

    @Bean
    public CacheSessionRepository cacheSessionRepository(SessionRepository sessionRepository){
        return new CacheSessionRepository(sessionRepository);
    }

    @Bean
    public AuthorisationSessionInterceptor authenticationInterceptor() {
        return new AuthorisationSessionInterceptor();
    }

    @Bean
    public MemberDao memberDao() {
        return new MemberRepository(sessionFactory);
    }

    @Bean
    PasswordService passwordService() {
        return new PasswordService();
    }

    @Bean
    public UserService userServiceImpl(MemberDao memberDao, PasswordService passwordService){
        return new UserService(memberDao, passwordService);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor());
    }
}
