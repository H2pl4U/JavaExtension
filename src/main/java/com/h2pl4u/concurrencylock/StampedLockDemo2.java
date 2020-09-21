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
 * 通过乐观锁来改善
 * 写操作一开始就抢到了锁，并写入了数据
 * 简而言之，StampedLock解决了在没有新数据写入时，
 * 由于过多读操作抢夺锁而使得写操作一直获取不到锁无法写入新数据的问题
 * Created by Liuwei on 2020/9/21 17:47
 */
public class StampedLockDemo2 {
    private static StampedLock lock = new StampedLock();
    private static List<Long> data = new ArrayList<>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        Runnable read = StampedLockDemo2::read;
        Runnable write = StampedLockDemo2::write;
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
        long stamped = lock.tryOptimisticRead();    //获取乐观锁
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //直接读取值
        String collect = data.stream().map(String::valueOf).collect(Collectors.joining(","));
        //如果戳改变了，方法返回false 说明stamped被修改过，被write方法修改过了，有新的数据写入
        //那么重新获取锁并去读取值，否则直接使用上面读取的值。
        if (!lock.validate(stamped)) {
            try {
                stamped = lock.readLock();
                TimeUnit.SECONDS.sleep(1);
                collect = data.stream().map(String::valueOf).collect(Collectors.joining(","));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlockRead(stamped);
            }
        }
        System.out.println(Thread.currentThread().getName() + " read value: " + collect);
    }
}
