package com.h2pl4u.concurrencylock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock方法是不可被打断的，即调用线程的interrupt方法不起作用
 * Created by Liuwei on 2020/9/21 17:00
 */
public class ReentrantLockDemo3 {
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(ReentrantLockDemo3::testLockUnInterruptibly);
        thread1.start();
        TimeUnit.SECONDS.sleep(1);

        Thread thread2 = new Thread(ReentrantLockDemo3::testLockUnInterruptibly);
        thread2.start();
        TimeUnit.SECONDS.sleep(1);

        thread2.interrupt();
    }

    private static void testLockUnInterruptibly() {
        try {
            lock.lock();    //不可被打断
            System.out.println(Thread.currentThread().getName() + "开始工作");
            while (true) {}
        } finally {
            lock.unlock();
        }
    }
}
