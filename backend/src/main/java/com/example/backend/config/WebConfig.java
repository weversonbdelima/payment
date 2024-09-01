package com.example.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Permite acesso a todas as APIs que começam com /api
                .allowedOrigins("http://localhost:4200") // Permite a origem do seu front-end
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite os métodos
                .allowedHeaders("*") // Permite todos os cabeçalhos
                .allowCredentials(true); // Permite o envio de cookies, se necessário
    }
}
