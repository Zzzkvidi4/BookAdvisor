package com.zzzkvidi4.bookadvisor.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/books/**", "/reviews/**").permitAll();
        http.authorizeRequests().antMatchers("/favourites/**").hasAnyRole("USER");
        http.exceptionHandling().authenticationEntryPoint(new RESTAuthenticationEntryPoint());
        http.formLogin().successHandler(new RESTAuthenticationSuccessHandler());
        http.formLogin().failureHandler(new RESTAuthenticationFailureHandler());
        http.logout();
        http.csrf().disable();
    }
}
