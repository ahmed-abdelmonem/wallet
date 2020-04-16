package com.leovegas.wallet.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * All Configurations related to end points security.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        // Create 2 users
        auth
            .inMemoryAuthentication()
            .withUser("user").password("$2a$04$mVFOVFGZtRPd7av3X.0IkOgj7JOT5lw/VAWFSK.MrCqi6q65czzeW").roles("USER")
            .and()
            .withUser("admin").password("$2a$04$N3/GOGRvSPF/5OO.9eoQTeF0YkHZIkv06nznXOYLZyJ3SqagNotuq").roles("USER", "ADMIN");

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception
    {    
        http.headers().frameOptions().disable(); //for H2 console
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // Secure the end points
        //HTTP Basic authentication
        http.httpBasic()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/wallet/**").hasRole("USER")
            .antMatchers(HttpMethod.POST, "/wallet/**").hasRole("ADMIN")
            .and()
            .csrf().disable()
            .formLogin().disable();
    }


    @Bean
    public BCryptPasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder();
    }

}
