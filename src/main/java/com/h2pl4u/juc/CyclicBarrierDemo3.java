package com.h2pl4u.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 抛出BrokenBarrierException异常时表示屏障破损，此时标志位broken=true。
 * 抛出BrokenBarrierException异常的情况主要有：
 * 1.其他等待的线程被中断，则当前线程抛出BrokenBarrierException异常；
 * 2.其他等待的线程超时，则当前线程抛出BrokenBarrierException异常；
 * 3.当前线程在等待时，其他线程调用CyclicBarrier.reset()方法，则当前线程抛出BrokenBarrierException异常
 * Created by Liuwei on 2020/9/21 11:36
 */
public class CyclicBarrierDemo3 {
    /**
     * 模拟情况1
     *
     * @throws InterruptedException
     */
    private static void test1() throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(2);
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread() + "开始执行");
                TimeUnit.SECONDS.sleep(3);
                barrier.await();
                System.out.println(Thread.currentThread() + "继续执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        Thread t2 = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread() + "开始执行");
                TimeUnit.SECONDS.sleep(1);
                barrier.await();
                System.out.println(Thread.currentThread() + "继续执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "t2");
        t2.start();
        TimeUnit.SECONDS.sleep(2);
        t2.interrupt();
    }

    /**
     * 模拟情况2
     *
     * @throws InterruptedException
     */
    private static void test2() throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(2);
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread() + "开始执行");
                TimeUnit.SECONDS.sleep(3);
                barrier.await();
                System.out.println(Thread.currentThread() + "继续执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread() + "开始执行");
                TimeUnit.SECONDS.sleep(1);
                barrier.await();
                System.out.println(Thread.currentThread() + "继续执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }

    /**
     * 模拟情况3
     *
     * @throws InterruptedException
     */
    private static void test3() throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(2);
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread() + "开始执行");
                TimeUnit.SECONDS.sleep(3);
                barrier.await();
                System.out.println(Thread.currentThread() + "继续执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread() + "开始执行");
                TimeUnit.SECONDS.sleep(1);
                barrier.await();
                System.out.println(Thread.currentThread() + "继续执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "t2").start();
        TimeUnit.SECONDS.sleep(2);
        barrier.reset();
    }

    /**
     * 从上面的三个例子中可以看到，无论是哪种情况导致屏障破坏，屏障点后面的代码都没有被执行，main方法也没有退出
     * 和CountDownLatch区别
     * 1.CountDownLatch：一个线程(或者多个)，等待另外N个线程完成某个事情之后才能执行；
     *   CyclicBarrier：N个线程相互等待，任何一个线程完成之前，所有的线程都必须等待。
     * 2.CountDownLatch：一次性的；
     *   CyclicBarrier：可以重复使用。
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        test1();
        test2();
        test3();
    }


}
