package com.h2pl4u.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 为CyclicBarrier设置过期时间
 * await的重载方法：await(long timeout, TimeUnit unit)可以设置最大等待时长，
 * 超出这个时间屏障还没有开启的话则抛出TimeoutException
 * Created by Liuwei on 2020/9/21 11:28
 */
public class CyclicBarrierDemo2 {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(2);
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                barrier.await();
                System.out.println(Thread.currentThread() + "继续执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        },"t1").start();
        new Thread(() -> {
            try {
                barrier.await(1, TimeUnit.SECONDS);
                System.out.println(Thread.currentThread() + "继续执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        },"t2").start();
    }
}
