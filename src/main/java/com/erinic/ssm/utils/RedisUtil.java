package com.erinic.ssm.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by asus on 2017/4/6.
 */
public class RedisUtil {


    private RedisTemplate<Serializable,Object> redisTemplate;

    public void remove(final String ... keys){
        for (String key : keys){
            remove(key);
        }
    }

    public void remove(final String key){
        if (exits(key)){
            redisTemplate.delete(key);
        }
    }

    public void removePattern(final String key){
        Set<Serializable> sets = redisTemplate.keys(key);
        if (sets.size() > 0){
            redisTemplate.delete(sets);
        }
    }

    public boolean exits(String key){
        return redisTemplate.hasKey(key);
    }

    public Object get(String key){
        Object result = null;
        ValueOperations<Serializable,Object> valueOperations = redisTemplate.opsForValue();
        result = valueOperations.get(key);
        return result;
    }

    public boolean set(final String key, Object value){
        boolean result = false;
        try{
            ValueOperations<Serializable,Object> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key,value);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public boolean set(final String key,Object value, Long expire){
        boolean result = false;
        try{
            ValueOperations<Serializable,Object> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key,value);
            redisTemplate.expire(key,expire, TimeUnit.SECONDS);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public void setRedisTemplate(RedisTemplate<Serializable,Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }
}
