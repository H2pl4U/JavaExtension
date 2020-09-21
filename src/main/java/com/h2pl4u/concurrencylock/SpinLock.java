package com.h2pl4u.concurrencylock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * JUC中并没有自旋锁对应的类，而所谓的自旋锁就是：尝试获取锁的线程不会马上阻塞，
 * 而是采用循环的方式去尝试获取锁。这种方式的好处是可以减少线程上下文切换的消耗，
 * 缺点是循环会消耗CPU资源
 * 利用CAS实现一个自旋锁:
 * Created by Liuwei on 2020/9/21 17:21
 */
public class SpinLock {
    AtomicReference<Thread> reference = new AtomicReference<>();

    public void lock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "尝试获取锁");
        while (!reference.compareAndSet(null, thread)) {
            //自旋锁就是利用CAS思想制造循环 block住代码
        }
        System.out.println(thread.getName() + "获取到了锁");
    }

    public void unlock() {
        Thread thread = Thread.currentThread();
        reference.compareAndSet(thread, null);
        System.out.println(thread.getName() + "释放锁");
    }

    public static void main(String[] args) {
        SpinLock lock = new SpinLock();
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "做某事...");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "做某事...");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }
}
