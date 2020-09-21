package com.h2pl4u.concurrencylock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * ReentrantLock又称为递归锁
 * 一个线程如果获取了某个方法的锁，这个方法内部即使调用了别的需要获取锁的方法，
 * 那么这个线程不需要再次等待获取锁，可以直接进去.
 * ReentrantLock可以对一个方法不限次的重复加锁，但解锁次数必须和加锁次数一致，
 * 否则锁永远不会被释放，别的线程将无法获取该方法的锁
 * Created by Liuwei on 2020/9/21 16:50
 */
public class ReentrantLockDemo2 {
    private static Lock lock = new ReentrantLock();

    /**
     * method1和method2都加了锁，线程0获取到了method1的锁后，内部可以直接调用method2，
     * 无需重新获取锁对象。synchronized也具有相同的特性
     *
     * @param args
     */
    public static void main(String[] args) {
        IntStream.rangeClosed(0,1).forEach(i->new Thread(ReentrantLockDemo2::method1, String.valueOf(i)).start());
    }

    private static void method1() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " invoked method1");
            method2();
        } finally {
            lock.unlock();
        }
    }

    private static void method2() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " invoked method2");
        } finally {
            lock.unlock();
        }
    }
}
