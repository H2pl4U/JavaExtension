package com.h2pl4u.threadlocal;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 演示多线程间独立
 * 在多个线程中使用ThreadLocal
 * ThreadLocal在每个线程间是相互独立的，threadLocal在thread1、thread2和main线程间都有一份独立拷贝
 * Created by Liuwei on 2020/9/21 16:10
 */
public class ThreadLocalDemo2 {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
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
