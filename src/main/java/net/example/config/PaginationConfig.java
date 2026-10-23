package net.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
public class PaginationConfig {

    /**
     * This bean provides global default for all endpoints with pagination IF PageableDefault annotation is not specified!
     */
    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer pageableCustomizer(PaginationProperties properties) {

        return resolver -> {
            resolver.setFallbackPageable(PageRequest.of(0, properties.defaultPageSize()));
            resolver.setMaxPageSize(properties.maxPageSize());
        };
    }

}
