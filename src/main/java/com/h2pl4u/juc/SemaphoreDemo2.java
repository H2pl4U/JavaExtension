package com.h2pl4u.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * acquire方法会抛出InterruptedException异常，说明这个方法是可以被打断的
 * 而通过acquireUninterruptibly方法去获取许可证是不可被打断的
 * Created by Liuwei on 2020/9/21 14:21
 */
public class SemaphoreDemo2 {
    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(1);
        Thread t1 = new Thread(() -> {
            try {
                semaphore.acquire(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //acquireUninterruptibly方法:程序并不会抛出InterruptedException，thread1会一直处于阻塞状态
        Thread t2 = new Thread(() -> {
            semaphore.acquireUninterruptibly(2);
        });
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        t1.interrupt();
        t2.start();
        TimeUnit.SECONDS.sleep(1);
        t2.interrupt();
    }
}
