package com.benz.redis.api.service;

import com.benz.redis.api.model.Product;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private RedisTemplate<String,Product> redisTemplate;
    private ChannelTopic channelTopic;

    public MessageService(RedisTemplate<String,Product> redisTemplate,ChannelTopic channelTopic)
    {
        this.redisTemplate=redisTemplate;
        this.channelTopic=channelTopic;
    }

    public void sendProduct(Product product)
    {
        redisTemplate.convertAndSend(channelTopic.getTopic(),product);
    }
}
