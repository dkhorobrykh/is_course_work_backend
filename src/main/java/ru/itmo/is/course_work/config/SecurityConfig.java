package ru.itmo.is.course_work.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.itmo.is.course_work.filter.ExceptionHandlerFilter;

import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {
  private final ExceptionHandlerFilter exceptionHandlerFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.httpBasic(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            authz ->
                authz
                    .requestMatchers(HttpMethod.OPTIONS)
                    .permitAll()
                    .requestMatchers(
                        HttpMethod.POST,
                        "/authorization/confirm",
                        "/authorization/token",
                        "/authorization/register")
                    .permitAll()
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
                    .permitAll()
                    .requestMatchers("/actuator/**", "/api/actuator/**")
                    .permitAll()
                    .requestMatchers("/demo/**")
                    .authenticated()
                    .anyRequest()
                    .authenticated())
        .addFilterBefore(exceptionHandlerFilter, UsernamePasswordAuthenticationFilter.class)
        .oauth2ResourceServer(oauth ->
            oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter()))
        );

    return http.build();
  }

    @Bean
    @SuppressWarnings("unchecked")
    JwtAuthenticationConverter jwtAuthConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            if (realmAccess == null) return List.of();

            List<String> roles = (List<String>) realmAccess.get("roles");

            return roles.stream()
                    .map(r -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + r))
                    .toList();
        });
        return converter;
    }
}
