package com.hyperionoj.common.service;


import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author Hyperion
 */
public interface RedisSever {
    /**
     * 设置数据
     *
     * @param key   键
     * @param value 值
     */
    void setRedisKV(String key, String value);

    /**
     * 设置数据
     *
     * @param key   键
     * @param value 值
     * @param time  时间
     */
    void setRedisKV(String key, String value, long time);

    /**
     * 设置数据
     *
     * @param key   键
     * @param value 值
     */
    void setRedisList(String key, List<String> value);

    /**
     * 设置数据
     *
     * @param key   键
     * @param value 值
     */
    void setRedisSet(String key, List<String> value);

    /**
     * 设置数据
     *
     * @param key   键
     * @param value 值
     */
    void setRedisZSet(String key, List<String> value);

    /**
     * 设置数据
     *
     * @param key   键
     * @param value 值
     */
    void setRedisHash(String key, HashMap<String, String> value);

    /**
     * 设置数据
     *
     * @param key   键
     * @param value 值
     */
    void setRedisObj(String key, Object value);

    /**
     * 获取数据
     *
     * @param key 键
     * @return value
     */
    String getRedisKV(String key);

    /**
     * 获取数据
     *
     * @param key   键
     * @param start 开始
     * @param end   结束
     * @return list
     */
    List<String> getRedisList(String key, long start, long end);

    /**
     * 获取数据
     *
     * @param key 键
     * @return Set
     */
    Set<String> getRedisSet(String key);

    /**
     * 获取数据
     *
     * @param key 键
     * @return ZSet
     */
    Set<String> getRedisZSet(String key);

    /**
     * 获取数据
     *
     * @param key   键
     * @param field
     * @return value
     */
    String getRedisHash(String key, String field);

    /**
     * 获取数据
     *
     * @param key 键
     * @return value
     */
    String getRedisObj(String key);

    /**
     * 删库
     */
    void flushDb();

    /**
     * 删除key
     *
     * @param key 键
     */
    void delKey(String key);

    /**
     * 开启事务支持
     *
     * @param op 参数
     */
    void setEnableTransactionSupport(Boolean op);

    /**
     * 开启事务
     */
    void multi();

    /**
     * 提交事务
     *
     * @return 是否运行成功
     */
    Boolean exec();

    /**
     * 获取键
     *
     * @param prefix 匹配前缀
     * @return 键值
     */
    Set<String> getKeys(String prefix);
}
