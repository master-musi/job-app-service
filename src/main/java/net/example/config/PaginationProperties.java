package net.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.pagination")
public record PaginationProperties(
        int defaultPageSize,
        int maxPageSize
) {}
