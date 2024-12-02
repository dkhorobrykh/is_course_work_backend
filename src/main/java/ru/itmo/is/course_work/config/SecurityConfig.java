package ru.itmo.is.course_work.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authz -> {

                            authz
                                    .anyRequest()
                                    .permitAll();

//                            authz
//                                    .antMatchers("/api/v1/**").authenticated()
//                                    .anyRequest().permitAll();
                        }
                );
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterAfter(headersFilter, JwtFilter.class)
//                .addFilterBefore(exceptionHandlerFilter, JwtFilter.class);


        return http.build();
    }
}

