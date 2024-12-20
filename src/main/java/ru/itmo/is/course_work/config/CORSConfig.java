package ru.itmo.is.course_work.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Component
public class CORSConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*")); // Разрешает запросы с любых доменов
        configuration.setAllowedMethods(Collections.singletonList("*")); // Разрешает любые методы
        configuration.setAllowedHeaders(Collections.singletonList("*")); // Разрешает любые заголовки
        configuration.setAllowCredentials(false); // Отключить отправку cookies/credentials, если не надо

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
