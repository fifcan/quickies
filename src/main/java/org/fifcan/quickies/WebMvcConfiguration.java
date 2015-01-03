package org.fifcan.quickies;

import org.fifcan.quickies.mongo.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Date;
import java.util.Map;

/**
 * Created by deft on 03/01/2015.
 */

@EnableAutoConfiguration
@ComponentScan
@Controller
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {


    @RequestMapping("/foo")
    public String foo() {
        throw new RuntimeException("Expected exception in controller");
    }

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(WebMvcConfiguration.class).run(args);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Configuration
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserDao userDao;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http

                // access-denied-page: this is the page users will be
                // redirected to when they try to access protected areas.
                .exceptionHandling()
                .accessDeniedPage("/403")
                .and()

                // The intercept-url configuration is where we specify what roles are allowed access to what areas.
                // We specifically force the connection to https for all the pages, although it could be sufficient
                // just on the login page. The access parameter is where the expressions are used to control which
                // roles can access specific areas. One of the most important things is the order of the intercept-urls,
                // the most catch-all type patterns should at the bottom of the list as the matches are executed
                // in the order they are configured below. So /** (anyRequest()) should always be at the bottom of the list.
                .authorizeRequests()
                .antMatchers("/login**").permitAll()
                .antMatchers("/admin/**").hasRole( "ADMIN" )
                .anyRequest().authenticated()
                .and()
                .requiresChannel()
                //.anyRequest().requiresSecure() // TODO force HTTPS
                .and()

                // This is where we configure our login form.
                // login-page: the page that contains the login screen
                // login-processing-url: this is the URL to which the login form should be submitted
                // default-target-url: the URL to which the user will be redirected if they login successfully
                // authentication-failure-url: the URL to which the user will be redirected if they fail login
                // username-parameter: the name of the request parameter which contains the username
                // password-parameter: the name of the request parameter which contains the password
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?err=1")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()

                // This is where the logout page and process is configured. The logout-url is the URL to send
                // the user to in order to logout, the logout-success-url is where they are taken if the logout
                // is successful, and the delete-cookies and invalidate-session make sure that we clean up after logout
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?out=1")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()

                // The session management is used to ensure the user only has one session. This isn't
                // compulsory but can add some extra security to your application.
                .sessionManagement()
                .invalidSessionUrl("/login?time=1")
                .maximumSessions(1);
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDao)
                    //.passwordEncoder(new ShaPasswordEncoder())
            ;
        }
    }
}