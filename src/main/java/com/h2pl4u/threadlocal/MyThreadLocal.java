package com.h2pl4u.threadlocal;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用Map来代替ThreadLocalMap，创建一个简易的类ThreadLocal实现
 * Created by Liuwei on 2020/9/21 16:18
 */
public class MyThreadLocal<T> {
    private final Map<Thread, T> threadLocalMap = new HashMap<>();

    public void set(T t) {
        synchronized (this) {
            Thread key = Thread.currentThread();
            threadLocalMap.put(key, t);
        }
    }

    public T get() {
        synchronized (this) {
            Thread key = Thread.currentThread();
            T t = threadLocalMap.get(key);
            if (t == null) {
                return initValue();
            } else {
                return t;
            }
        }
    }

    public T initValue() {
        return null;
    }
}
