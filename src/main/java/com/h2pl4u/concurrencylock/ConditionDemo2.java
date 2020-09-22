package com.h2pl4u.concurrencylock;

import java.util.concurrent.TimeUnit;

/**
 * Condition的功能类似于Object对象的wait和notify方法，
 * 下面使用Object对象的wait和notify方法实现一个类似上面生产消费的功能
 * Created by Liuwei on 2020/9/22 9:21
 */
public class ConditionDemo2 {
    private static int data = 0;
    private static volatile boolean used = false;
    private final static Object MONITOR = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                produceData();
            }
        }, "t1").start();

        new Thread(() -> {
            while (true) {
                consumeData();
            }
        }, "t2").start();
    }

    private static void produceData() {
        synchronized (MONITOR) {
            while (!used) {
                try {
                    MONITOR.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            data++;
            System.out.println(Thread.currentThread().getName() + " 生产data = " + data);
            used = false;
            MONITOR.notifyAll();
        }
    }

    private static void consumeData() {
        synchronized (MONITOR) {
            while (used) {
                try {
                    MONITOR.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 消费data = " + data);
            used = true;
            MONITOR.notifyAll();
        }
    }
}
