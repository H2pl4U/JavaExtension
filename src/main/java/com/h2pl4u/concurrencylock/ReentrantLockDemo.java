package com.h2pl4u.concurrencylock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * Created by Liuwei on 2020/9/21 16:39
 */
public class ReentrantLockDemo {
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        IntStream.range(0, 2).forEach(i -> new Thread(ReentrantLockDemo::needLock).start());
        //等效的synchronized关键字实现锁方法
        synchronized (ReentrantLockDemo.class) {
            System.out.println(Thread.currentThread().getName() + "开始工作");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void needLock() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始工作");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
