package com.hyperionoj.admin.aop;

import java.lang.annotation.*;

/**
 * @author Hyperion
 * @date 2021/12/10
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdminActionAnnotation {

    String url() default "";

}
