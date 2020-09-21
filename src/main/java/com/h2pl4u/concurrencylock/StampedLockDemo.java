package com.h2pl4u.concurrencylock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * JDK8 新增了一个锁StampedLock，它是对ReadWriteLock的改进
 * 使用ReadWriteLock的时候，当读线程数量远大于写线程数量的时候就会出现“写饥饿”现象。
 * 因为锁大概率都被读线程抢走了，写线程很难抢到锁，这将使得读写效率非常低下。
 * JDK8的StampedLock就是为了解决这个问题而设计的，StampedLock包含乐观锁和悲观锁：
 * 乐观锁：每次去拿数据的时候，并不获取锁对象，而是判断标记位（stamp）是否有被修改，如果有修改就再去读一次。
 * 悲观锁：每次拿数据的时候都去获取锁。
 * 通过乐观锁，当写线程没有写数据的时候，标志位stamp并没有改变，所以即使有再多的读线程在读取数据，
 * 它们都可以直接去读数据，而无需获取锁，这就不会使得写线程抢不到锁了
 * (stamp类似一个时间戳的作用，每次写的时候对其+1来改变被操作对象的stamp值。)
 * Created by Liuwei on 2020/9/21 17:36
 */
public class StampedLockDemo {
    private static StampedLock lock = new StampedLock();
    private static List<Long> data = new ArrayList<>();

    //写线程最后才抢到锁并写入数据
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        Runnable read = StampedLockDemo::read;
        Runnable write = StampedLockDemo::write;
        IntStream.range(0, 19).forEach(i -> executorService.submit(read));
        executorService.submit(write);
        executorService.shutdown();
    }

    private static void write() {
        long stamped = -1;
        try {
            stamped = lock.writeLock();
            TimeUnit.SECONDS.sleep(1);
            long value = System.currentTimeMillis();
            data.add(value);
            System.out.println(Thread.currentThread().getName() + " write value: " + value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlockWrite(stamped);
        }
    }

    private static void read() {
        long stamped = -1;
        try {
            stamped = lock.readLock();  //获取悲观锁 阻塞写线程
            TimeUnit.SECONDS.sleep(1);
            String collect = data.stream().map(String::valueOf).collect(Collectors.joining(","));
            System.out.println(Thread.currentThread().getName() + " read value: " + collect);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlockRead(stamped);
        }
    }
}
