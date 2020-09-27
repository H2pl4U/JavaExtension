package com.h2pl4u.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 当线程池中所有任务都处理完毕后，线程并不会自己关闭。通过调用shutdown和shutdownNow方法来关闭线程池。
 * 两者的区别在于：
 * 1.shutdown方法将线程池置为shutdown状态，拒绝新的任务提交，但线程池并不会马上关闭，而是等待所有正
 * 在执行的和线程队列里的任务都执行完毕后，线程池才会被关闭。所以这个方法是平滑的关闭线程池。
 * 2.shutdownNow方法将线程池置为stop状态，拒绝新的任务提交，中断正在执行的那些任务，并且清除线程队列
 * 里的任务并返回。所以这个方法是比较“暴力”的。
 * Created by Liuwei on 2020/9/27 9:43
 */
public class ThreadPoolShutdownDemo {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2, 4, 10,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(2),
                Thread::new, new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.execute(new shortTask());
        threadPoolExecutor.execute(new longTask());
        threadPoolExecutor.execute(new longTask());
        threadPoolExecutor.execute(new shortTask());

        threadPoolExecutor.shutdown();
        System.out.println("已经执行了线程池shutdown方法");

        //常与shutdown搭配的方法有awaitTermination,awaitTermination方法接收timeout和TimeUnit两个参数，
        // 用于设定超时时间及单位。当等待超过设定时间时，会监测ExecutorService是否已经关闭，若关闭则返回true，
        // 否则返回false
        boolean isShutdown = threadPoolExecutor.awaitTermination(3, TimeUnit.SECONDS);
        if (isShutdown) {
            System.out.println("线程池在3秒内成功关闭");
        } else {
            System.out.println("线程池在3秒内未关闭");
        }
        System.out.println("------------");
    }

    private static class shortTask implements Runnable {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "执行shortTask完毕");
            } catch (InterruptedException e) {
                System.err.println("shortTask执行过程中被打断" + e.getMessage());
            }
        }
    }

    private static class longTask implements Runnable {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "执行longTask完毕");
            } catch (InterruptedException e) {
                System.err.println("longTask执行过程中被打断" + e.getMessage());
            }
        }
    }
}
