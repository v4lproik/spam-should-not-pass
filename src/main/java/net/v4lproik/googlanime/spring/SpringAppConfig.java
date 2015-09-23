package net.v4lproik.googlanime.spring;

import net.v4lproik.googlanime.platform.client.crawler.Crawler;
import net.v4lproik.googlanime.platform.dao.api.MemberDao;
import net.v4lproik.googlanime.platform.dao.repositories.CacheSessionRepository;
import net.v4lproik.googlanime.platform.dao.repositories.MemberRepository;
import net.v4lproik.googlanime.spring.interceptor.AuthorisationSessionInterceptor;
import net.v4lproik.googlanime.platform.service.api.PasswordService;
import net.v4lproik.googlanime.platform.service.api.UserService;
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
@ComponentScan("net.v4lproik.googlanime")
@EnableWebMvc
public class SpringAppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    Crawler defaultCrawler;

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
