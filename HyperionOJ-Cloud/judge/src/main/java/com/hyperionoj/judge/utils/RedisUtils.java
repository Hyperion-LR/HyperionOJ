package com.hyperionoj.judge.utils;

import com.alibaba.fastjson.JSON;
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
public class RedisUtils {

    private static Random random = new Random();
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void setRedisKV(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void setRedisKV(String key, String value, long time) {
        stringRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    public void setRedisList(String key, List<String> value) {
        for (String item : value) {
            stringRedisTemplate.opsForList().rightPush(key, item);
        }
    }

    public void setRedisSet(String key, List<String> value) {
        for (String item : value) {
            stringRedisTemplate.opsForSet().add(key, item);
        }
    }

    public void setRedisZSet(String key, List<String> value) {
        for (String item : value) {
            stringRedisTemplate.opsForZSet().add(key, item, 1);
        }
    }

    public void setRedisHash(String key, HashMap<String, String> value) {
        stringRedisTemplate.opsForHash().putAll(key, value);
    }

    public void setRedisObj(String key, Object value) {
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(value));
    }

    public String getRedisKV(String key) {
        stringRedisTemplate.expire(key, Duration.ofMinutes(30 + random.nextInt(15)));
        return stringRedisTemplate.opsForValue().get(key);
    }

    public List<String> getRedisList(String key, long start, long end) {
        stringRedisTemplate.expire(key, Duration.ofMinutes(30 + random.nextInt(15)));
        return stringRedisTemplate.opsForList().range(key, start, end);
    }

    public Set<String> getRedisSet(String key) {
        stringRedisTemplate.expire(key, Duration.ofMinutes(30 + random.nextInt(15)));
        return stringRedisTemplate.opsForSet().members(key);
    }

    public Set<String> getRedisZSet(String key) {
        stringRedisTemplate.expire(key, Duration.ofMinutes(30 + random.nextInt(15)));
        return stringRedisTemplate.opsForZSet().range(key, 0, -1);
    }

    public String getRedisHash(String key, String field) {
        stringRedisTemplate.expire(key, Duration.ofMinutes(30 + random.nextInt(15)));
        return (String) stringRedisTemplate.opsForHash().get(key, field);
    }

    public String getRedisObj(String key) {
        stringRedisTemplate.expire(key, Duration.ofMinutes(30 + random.nextInt(15)));
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void flushDb() {
        Set<String> keys = stringRedisTemplate.keys("*");
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            stringRedisTemplate.delete(it.next());
        }
    }

    public void delKey(String key) {
        stringRedisTemplate.delete(key);
    }

    public void setEnableTransactionSupport(Boolean op) {
        stringRedisTemplate.setEnableTransactionSupport(op);
    }

    public void multi() {
        stringRedisTemplate.multi();
    }


    public Boolean exec() {
        Boolean res = true;
        List<Object> exec = stringRedisTemplate.exec();
        for (Object o : exec) {
            res &= (boolean) o;
        }
        return res;
    }

    public Set<String> getKeys(String prefix) {
        return stringRedisTemplate.keys(prefix.concat("*"));
    }
}
