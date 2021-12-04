package com.hyperionoj.common.utils;

/**
 * @author Hyperion
 * @date 2021/12/2
 */
public class ThreadLocalUtils {

    private static final ThreadLocal<Object> THREAD_LOCAL = new ThreadLocal<>();

    private ThreadLocalUtils() {

    }

    public static void set(Object object) {
        THREAD_LOCAL.set(object);
    }

    public static Object get() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
