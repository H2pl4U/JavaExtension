package com.h2pl4u.threadlocal;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal基本原理
 * 在ThreadLocal类中有一个静态内部类ThreadLocalMap(概念上类似于Map)，用键值对的形式存储每一个线程的变量副本，
 * ThreadLocalMap中元素的key为当前ThreadLocal对象，而value对应线程的变量副本。
 * Created by Liuwei on 2020/9/21 16:16
 */
public class ThreadLocalDemo3 {
    private static MyThreadLocal<String> threadLocal = new MyThreadLocal<String>() {
        @Override
        public String initValue() {
            return "initValue";
        }
    };
    private static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            threadLocal.set("thread t1");
            try {
                TimeUnit.MICROSECONDS.sleep(random.nextInt(1000));
                System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread1");
        Thread thread2 = new Thread(() -> {
            threadLocal.set("thread t2");
            try {
                TimeUnit.MICROSECONDS.sleep(random.nextInt(1000));
                System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread2");
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
    }
}
