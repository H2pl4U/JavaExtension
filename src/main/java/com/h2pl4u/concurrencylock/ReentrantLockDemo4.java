package com.h2pl4u.concurrencylock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock提供了可打断获取锁的方法lockInterruptibly
 * thread2在等待获取锁时被打断，抛出InterruptedException异常
 * Created by Liuwei on 2020/9/21 17:05
 */
public class ReentrantLockDemo4 {
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(ReentrantLockDemo4::testLockInterruptibly);
        thread1.start();
        TimeUnit.SECONDS.sleep(1);

        Thread thread2 = new Thread(ReentrantLockDemo4::testLockInterruptibly);
        thread2.start();
        TimeUnit.SECONDS.sleep(1);

        thread2.interrupt();
    }

    private static void testLockInterruptibly() {
        try {
            lock.lockInterruptibly();
            System.out.println(Thread.currentThread().getName() + "开始工作");
            while (true) {
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
