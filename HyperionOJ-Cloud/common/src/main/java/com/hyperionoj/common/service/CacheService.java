package com.hyperionoj.common.service;

/**
 * 当某个操作并不通过缓存但可能会修改Redis缓存时
 *
 * @author Hyperion
 * @date 2021/12/14
 */
public interface CacheService {

    /**
     *
     */
    void update();

}
