package com.h2pl4u.juc;

/**
 * volatile 有序性 利用双重校验锁的单例模式举例
 * 通过volatile修饰的成员变量会添加内存屏障来阻止JVM进行指令重排优化
 * Created by Liuwei on 2020/9/21 14:05
 */
public class VolatileDemo2 {
    private VolatileDemo2() {
    }

    //单例对象
    private volatile static VolatileDemo2 instance = null;

    //静态工厂方法
    public static VolatileDemo2 getInstance() {
        if (instance == null) {     //双重校验锁
            synchronized (VolatileDemo2.class) {    //同步锁
                if (instance == null) {
                    instance = new VolatileDemo2();
                }
            }
        }
        return instance;
    }
}
