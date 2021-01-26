package com.benz.redis.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "redis.env")
@Getter
@Setter
public class EnvironmentConfig {

    private String host;
    private int port;
}
