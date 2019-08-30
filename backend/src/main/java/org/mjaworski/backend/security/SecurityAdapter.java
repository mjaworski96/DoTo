package org.mjaworski.backend.security;

import org.mjaworski.backend.security.ApplicationAuthenticationEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityAdapter extends WebSecurityConfigurerAdapter {
    private static final String[] AUTH_WHITELIST_GET = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/actuator/**",
    };
    private static final String[] AUTH_WHITELIST_POST = {
            "/api/login/**",
    };
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, AUTH_WHITELIST_GET)
                .permitAll()
                .antMatchers(HttpMethod.POST, AUTH_WHITELIST_POST)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(new JWTLoginFilter("/api/login", authenticationManager(), getApplicationContext()),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthenticationFilter(getApplicationContext()),
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new ApplicationAuthenticationEntryPoint())
                .accessDeniedHandler(new ForbiddenHandler());

    }

}