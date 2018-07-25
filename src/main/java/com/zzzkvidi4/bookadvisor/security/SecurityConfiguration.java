package com.zzzkvidi4.bookadvisor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private CustomUserDetailsService userDetailsService;
    private PasswordEncoder encoder;
    private AuthenticationSuccessHandler successHandler;
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    public void setUserDetailsService(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setEncoder(PasswordEncoder encoder){
        this.encoder = encoder;
    }

    public CustomAuthenticationFilter authenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(successHandler);
        return filter;
    }

    @Autowired
    public void setSuccessHandler(AuthenticationSuccessHandler successHandler){
        this.successHandler = successHandler;
    }

    @Autowired
    public void setLogoutSuccessHandler(LogoutSuccessHandler logoutSuccessHandler){
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/books/**", "/reviews/**", "/users").permitAll();
        http.authorizeRequests().antMatchers("/users/check-login", "/users/is-logged-in").permitAll();
        http.authorizeRequests().antMatchers("/users/**").hasAnyRole("USER");
        http.exceptionHandling().authenticationEntryPoint(new RESTAuthenticationEntryPoint());
        http.formLogin().successHandler(successHandler);
        http.formLogin().failureHandler(new RESTAuthenticationFailureHandler());
        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessHandler(logoutSuccessHandler);
        http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
