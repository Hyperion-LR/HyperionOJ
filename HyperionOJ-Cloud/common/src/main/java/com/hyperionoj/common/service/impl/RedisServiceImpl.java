package com.hyperionoj.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.hyperionoj.common.service.RedisSever;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
@Service
public class RedisServiceImpl implements RedisSever {

    private static Random random = new Random();
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void setRedisKV(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void setRedisKV(String key, String value, long time) {
        stringRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    @Override
    public void setRedisList(String key, List<String> value) {
        for (String item : value) {
            stringRedisTemplate.opsForList().rightPush(key, item);
        }
    }

    @Override
    public void setRedisSet(String key, List<String> value) {
        for (String item : value) {
            stringRedisTemplate.opsForSet().add(key, item);
        }
    }

    @Override
    public void setRedisZSet(String key, List<String> value) {
        for (String item : value) {
            stringRedisTemplate.opsForZSet().add(key, item, 1);
        }
    }

    @Override
    public void setRedisHash(String key, HashMap<String, String> value) {
        stringRedisTemplate.opsForHash().putAll(key, value);
    }

    @Override
    public void setRedisObj(String key, Object value) {
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(value));
    }

    @Override
    public String getRedisKV(String key) {
        stringRedisTemplate.expire(key, Duration.ofMinutes(30 + random.nextInt(15)));
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public List<String> getRedisList(String key, long start, long end) {
        stringRedisTemplate.expire(key, Duration.ofMinutes(30 + random.nextInt(15)));
        return stringRedisTemplate.opsForList().range(key, start, end);
    }

    @Override
    public Set<String> getRedisSet(String key) {
        stringRedisTemplate.expire(key, Duration.ofMinutes(30 + random.nextInt(15)));
        return stringRedisTemplate.opsForSet().members(key);
    }

    @Override
    public Set<String> getRedisZSet(String key) {
        stringRedisTemplate.expire(key, Duration.ofMinutes(30 + random.nextInt(15)));
        return stringRedisTemplate.opsForZSet().range(key, 0, -1);
    }

    @Override
    public String getRedisHash(String key, String field) {
        stringRedisTemplate.expire(key, Duration.ofMinutes(30 + random.nextInt(15)));
        return (String) stringRedisTemplate.opsForHash().get(key, field);
    }

    @Override
    public String getRedisObj(String key) {
        stringRedisTemplate.expire(key, Duration.ofMinutes(30 + random.nextInt(15)));
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void flushDb() {
        Set<String> keys = stringRedisTemplate.keys("*");
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            stringRedisTemplate.delete(it.next());
        }
    }

    @Override
    public void delKey(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public void setEnableTransactionSupport(Boolean op) {
        stringRedisTemplate.setEnableTransactionSupport(op);
    }

    @Override
    public void multi() {
        stringRedisTemplate.multi();
    }


    @Override
    public Boolean exec() {
        Boolean res = true;
        List<Object> exec = stringRedisTemplate.exec();
        for (Object o : exec) {
            res &= (boolean) o;
        }
        return res;
    }

    @Override
    public Set<String> getKeys(String prefix) {
        return stringRedisTemplate.keys(prefix.concat("*"));
    }
}
