package com.steamyao.newcode.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class JedisAdapter {

    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

    @Autowired
    private RedisTemplate redisTemplate;


    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }



    public void addQuesViewNum(String key){
        if (redisTemplate.hasKey(key)){
            redisTemplate.opsForValue().increment(key);
        }else {
            redisTemplate.opsForValue().set(key,1);
        }
    }

    public int getViewNum(String key){
        return  (int)redisTemplate.opsForValue().get(key);
    }




    public long sadd(String key, String value) {
        try {
            return redisTemplate.opsForSet().add(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return 0;
    }

    public long srem(String key, String value) {
        try {
            return redisTemplate.opsForSet().remove(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return 0;
    }

    public long scard(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return 0;
    }

    public boolean sismember(String key, String value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return false;
    }

    public String sendMsg(String topic,String value) {
        try {

            redisTemplate.convertAndSend(topic,value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return null;
    }


    public long lpush(String key, String value) {
        try {
            return redisTemplate.opsForList().leftPush(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return 0;
    }

    public List<String> lrange(String key, int start, int end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return null;
    }

    public boolean zadd(String key, String value, double score) {
        try {
            return redisTemplate.opsForZSet().add(key, value,score);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return false;
        }

    }

    public long zrem(String key, String value) {
        try {
           return redisTemplate.opsForZSet().remove(key,value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return 0;
        }

    }


    public Set<String> zrange(String key, int start, int end) {
        try {
            return redisTemplate.opsForZSet().range(key,start,end);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return null;
    }

    public Set<String> zrevrange(String key, int start, int end) {
        try {
            return redisTemplate.opsForZSet().range(key,start,end);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return null;
    }

    public long zcard(String key) {
        try {
            return redisTemplate.opsForZSet().zCard(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return 0;
    }

    public Double zscore(String key, String member) {
        try {
            return redisTemplate.opsForZSet().score(key,member);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        }
        return null;
    }


}