package com.benz.redis.api;

import com.benz.redis.api.config.EnvironmentConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(EnvironmentConfig.class)
public class RedisWithBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisWithBootApplication.class, args);
    }

}
