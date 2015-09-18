package net.v4lproik.googlanime.spring;

import net.v4lproik.googlanime.dao.api.MemberDao;
import net.v4lproik.googlanime.dao.repositories.MemberRepository;
import net.v4lproik.googlanime.interceptor.AuthorisationSessionInterceptor;
import net.v4lproik.googlanime.service.api.PasswordService;
import net.v4lproik.googlanime.service.api.UserService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan("net.v4lproik.googlanime")
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    SessionFactory sessionFactory;

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
