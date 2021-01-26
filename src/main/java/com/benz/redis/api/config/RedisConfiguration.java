package com.benz.redis.api.config;

import com.benz.redis.api.model.Product;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfiguration {

    @Bean(name = "productConfig")
    public RedisTemplate<String, Product> getProductConfig(JedisConnectionFactory jedisConnectionFactory)
    {
        RedisTemplate<String,Product> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<String,Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory)
    {
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public ChannelTopic channelTopic()
    {
        return new ChannelTopic("PRODUCT_TOPIC");
    }

    private Jackson2JsonRedisSerializer<Product> jackson2JsonRedisSerializer()
    {
        return new Jackson2JsonRedisSerializer<Product>(Product.class);
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(EnvironmentConfig env)
    {
        return new JedisConnectionFactory(new RedisStandaloneConfiguration(env.getHost(),env.getPort()));
    }
}
