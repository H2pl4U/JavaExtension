package com.h2pl4u.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * drainPermits方法一次性获取所有许可证（drain抽干榨干）
 * availablePermits方法用于获取当前可用许可证数量的预估值
 * Created by Liuwei on 2020/9/21 14:27
 */
public class SemaphoreDemo3 {
    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(5);

        new Thread(() -> {
            System.out.println("availablePermits:" + semaphore.availablePermits());
            semaphore.drainPermits(); //获取所有许可证(抽干榨干)
            System.out.println("availablePermits:" + semaphore.availablePermits());
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphore.release(5);
            System.out.println(Thread.currentThread().getName() + "结束");
        }, "t1").start();
    }
}
