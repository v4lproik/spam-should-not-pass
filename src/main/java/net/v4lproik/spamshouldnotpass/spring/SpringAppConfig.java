package net.v4lproik.spamshouldnotpass.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.v4lproik.spamshouldnotpass.platform.dao.api.UserDao;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.CacheSessionRepository;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.UserRepository;
import net.v4lproik.spamshouldnotpass.platform.service.api.PasswordService;
import net.v4lproik.spamshouldnotpass.platform.service.api.SchemeService;
import net.v4lproik.spamshouldnotpass.platform.service.api.UserService;
import net.v4lproik.spamshouldnotpass.spring.interceptor.AuthorisationSessionInterceptor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
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

    //=========== REPOSITORY ===========//

    @Autowired
    SessionRepository sessionRepository;

    @Bean
    public CacheSessionRepository cacheSessionRepository(SessionRepository sessionRepository){
        return new CacheSessionRepository(sessionRepository);
    }

    //=========== MAPPER ===========//

    @Bean
    public ExpressionParser parser() {
        return new SpelExpressionParser();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


    //=========== INTERCEPTOR ===========//

    @Bean
    public AuthorisationSessionInterceptor authenticationInterceptor() {
        return new AuthorisationSessionInterceptor();
    }



    //=========== DAO ===========//

    @Bean
    public UserDao userDao() {
        return new UserRepository(sessionFactory);
    }



    //=========== SERVICE ===========//

    @Bean
    PasswordService passwordService() {
        return new PasswordService();
    }

    @Bean
    SchemeService schemeService() {
        return new SchemeService();
    }

    @Bean
    public UserService userService(UserDao userDao, PasswordService passwordService){
        return new UserService(userDao, passwordService);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor());
    }
}
