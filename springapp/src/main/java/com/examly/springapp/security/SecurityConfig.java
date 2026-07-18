package com.examly.springapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf().disable()
            .cors().and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()

                // Public APIs
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/notices/all").permitAll()
                .antMatchers(HttpMethod.GET, "/api/notices/*").permitAll()

                // Admin APIs
                .antMatchers("/api/admin/**").hasRole("ADMIN")

                // Notice APIs
                .antMatchers(HttpMethod.POST, "/api/notices/add")
                    .hasAnyRole("ADMIN", "PRINCIPAL", "TEACHER", "DEPARTMENT_HEAD")

                .antMatchers(HttpMethod.PUT, "/api/notices/update/**")
                    .hasAnyRole("ADMIN", "PRINCIPAL", "TEACHER", "DEPARTMENT_HEAD")

                .antMatchers(HttpMethod.DELETE, "/api/notices/delete/**")
                    .hasAnyRole("ADMIN", "PRINCIPAL")

                .antMatchers("/api/notices/*/approve")
                    .hasAnyRole("ADMIN", "PRINCIPAL", "DEPARTMENT_HEAD")

                .antMatchers("/api/notices/*/publish")
                    .hasAnyRole("ADMIN", "PRINCIPAL")

                .anyRequest().authenticated()
            .and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }
}