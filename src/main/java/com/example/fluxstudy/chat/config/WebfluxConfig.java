package com.example.fluxstudy.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebfluxConfig implements WebFluxConfigurer {
    /**
     * see: https://www.baeldung.com/spring-webflux-cors
     * https://tecoble.techcourse.co.kr/post/2022-10-11-server-sent-events/
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedHeaders("Accept", "Cache-Control", "Content-Type")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .exposedHeaders("Content-Type", "Transfer-Encoding");
    }
}
