package com.steamyao.newcode.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.steamyao.newcode.asyn.EventConsumer;
import com.steamyao.newcode.serializer.JodaDateTimeJsonDeserializer;
import com.steamyao.newcode.serializer.JodaDateTimeJsonSerialize;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * @Package com.steamyao.miaosha.config
 * @date 2019/7/27 7:54
 * @description
 */
@Component
public class RedisConfig {

    //返回自定义的 redisTemplate,不使用springboot 默认的
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //设置key的序列化方式为Sting
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);

        //设置value的序列化方式
        Jackson2JsonRedisSerializer jsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(DateTime.class,new JodaDateTimeJsonSerialize());
        simpleModule.addDeserializer(DateTime.class,new JodaDateTimeJsonDeserializer());
        objectMapper.registerModule(simpleModule);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jsonRedisSerializer.setObjectMapper(objectMapper);

        redisTemplate.setValueSerializer(jsonRedisSerializer);

        return redisTemplate;
    }


    /**
     * 创建连接工厂
     *
     * @param connectionFactory
     * @param adapter
     * @return
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter adapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(adapter, new PatternTopic("FEED_TOPIC"));
        return container;
    }

    /**
     * @param message
     * @return
     */
    @Bean
    public MessageListenerAdapter adapter(EventConsumer message){
        // onMessage EventConsumer 中 没有实现接口，这个参数必须跟EventConsumer中的读取信息的方法名称一样
        return new MessageListenerAdapter(message, "onMessage");
    }

}
