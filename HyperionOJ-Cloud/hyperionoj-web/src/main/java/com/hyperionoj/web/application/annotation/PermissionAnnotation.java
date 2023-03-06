package com.hyperionoj.web.application.annotation;

import java.lang.annotation.*;

/**
 * @author Hyperion
 * @date 2021/12/10
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionAnnotation {
    /**
     * 执行方法需要的权限等级
     */
    int level() default 0;
}
