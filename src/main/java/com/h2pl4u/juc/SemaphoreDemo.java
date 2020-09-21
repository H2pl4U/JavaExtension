package com.h2pl4u.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * JUC的Semaphore俗称信号量，可用来控制同时访问特定资源的线程数量。
 * 通过它的构造函数我们可以指定信号量（称为许可证permits可能更为明确）
 * 的数量，线程可以调用Semaphore对象的acquire方法获取一个许可证，
 * 调用release来归还一个许可证
 * Created by Liuwei on 2020/9/21 14:10
 */
public class SemaphoreDemo {

    /**
     * acquire的重载方法acquire(int permits)允许线程一次性获取N个许可证；
     * 同样的release的重载方法release(int permits)允许线程一次性释放N个许可证。
     * Semaphore还有一个tryAcquire，它允许线程尝试去获取1个许可证，如果许可证不足没有获取到的话，
     * 线程也会继续执行，而非阻塞等待。tryAcquire方法的重载方法
     * tryAcquire(long timeout, TimeUnit unit)可以指定尝试获取许可证的超时时间
     * @param args
     */
    public static void main(String[] args) {
        //定义许可证数量
        final Semaphore semaphore = new Semaphore(2);
        IntStream.range(0, 4).forEach(i -> {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 开始");
                    semaphore.acquire(); //一次拿一个许可证
                    System.out.println(Thread.currentThread().getName() + "获取许可证");
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(Thread.currentThread().getName() + "释放许可证");
                    semaphore.release();
                }
                System.out.println(Thread.currentThread().getName() + "结束");
            },"t" + (i + 1)).start();
        });
    }
}
