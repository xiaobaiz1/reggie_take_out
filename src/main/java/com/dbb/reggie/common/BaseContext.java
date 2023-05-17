package com.dbb.reggie.common;

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登录用户Id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrenId() {
        return threadLocal.get();
    }
}
