package com.backend.backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow CORS for all endpoints
            .allowedOrigins("*") // Allow requests from any origin (you can specify your frontend URL)
            .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specific HTTP methods
            .allowedHeaders("*"); // Allow all headers
    }
}
