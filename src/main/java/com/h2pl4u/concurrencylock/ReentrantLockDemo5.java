package com.h2pl4u.concurrencylock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock的tryLock方法用于尝试获取锁，返回boolean类型，表示获取锁成功与否
 * thread1抢到锁后进入死循环，一直不释放锁。thread2尝试获取锁失败后直接放弃。
 * tryLock的重载方法tryLock(long timeout, TimeUnit unit)可以设置尝试获取锁的
 * 时间范围，超过这个时间没有获取到锁则返回false。
 * Created by Liuwei on 2020/9/21 17:16
 */
public class ReentrantLockDemo5 {
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(ReentrantLockDemo5::testTryLock, "thread1");
        thread1.start();
        TimeUnit.SECONDS.sleep(1);

        Thread thread2 = new Thread(ReentrantLockDemo5::testTryLock, "thread2");
        thread2.start();
    }

    private static void testTryLock() {
        if (lock.tryLock()) {
            try {
                System.out.println(Thread.currentThread().getName() + "开始工作");
                while (true) {
                }
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + "没有获取到锁");
        }
    }
}
