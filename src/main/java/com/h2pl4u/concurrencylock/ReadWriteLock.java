package com.h2pl4u.concurrencylock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;


/**
 * ReadWriteLock为读写锁。ReentrantLock为排他锁，同一时刻只允许一个线程进行访问，
 * 而读写锁在同一时刻可以允许多个读线程访问，但是在写线程访问时，所有的读线程和其他写
 * 线程均被阻塞。读写锁维护了一对锁，一个读锁和一个写锁，通过分离读锁和写锁，使得并发
 * 性相比一般的排他锁有了很大提升。简而言之，ReadWriteLock包含读写锁，遵循以下规则：
 * 1.写的时候不能读
 * 2.写的时候不能写
 * 3.读的时候不能写
 * 4.读的时候可以读
 * Created by Liuwei on 2020/9/21 17:26
 */
public class ReadWriteLock {
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    //读锁
    private static ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    //写锁
    private static ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    //存放数据
    private static List<Long> data = new ArrayList<>();

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                write();
            }
        }, "writer").start();

        new Thread(() -> {
            while (true) {
                read();
            }
        }, "reader").start();
    }

    public static void write() {
        try {
            writeLock.lock();   //写锁
            TimeUnit.SECONDS.sleep(1);
            long value = System.currentTimeMillis();
            data.add(value);
            System.out.println(Thread.currentThread().getName() + " 写入value: " + value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock(); //释放写锁
        }
    }

    private static void read() {
        try {
            readLock.lock();    //获取读锁
            TimeUnit.SECONDS.sleep(1);
            String value = data.stream().map(String::valueOf).collect(Collectors.joining(","));
            System.out.println(Thread.currentThread().getName() + "读取data: " + value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();  //释放读锁
        }
    }
}
