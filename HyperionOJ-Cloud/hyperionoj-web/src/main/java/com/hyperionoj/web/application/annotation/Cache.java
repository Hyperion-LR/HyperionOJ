package com.hyperionoj.web.application.annotation;

import java.lang.annotation.*;

/**
 * @author Hyperion
 * @date 2021/12/12
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    /**
     * 缓存时间
     */
    long time() default 0;

    /**
     * 缓存名称
     */
    String name() default "";

}
