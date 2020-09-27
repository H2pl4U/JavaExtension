package com.h2pl4u.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 这是一个理论上无限大小的线程池：
 * 1.核心线程数为0，SynchronousQueue队列是没有长度的队列，所以当有新的任务提交，
 * 如果有空闲的还未超时的（最大空闲时间60秒）线程则执行该任务，否则新增一个线程来处理该任务。
 * 2.因为线程数量没有限制，理论上可以接收无限个新任务，所以这里也没有指定拒绝策略。
 * Created by Liuwei on 2020/9/27 10:43
 */
public class NewCachedThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println("无限大小的线程池创建完毕");
        //通过execute方法向线程池提交1个任务
        executorService.execute(() -> sleep(100));
        //通过execute方法向线程池提交2个任务
        executorService.execute(() -> sleep(100));
    }

    private static void sleep(long value) {
        try {
            System.out.println(Thread.currentThread().getName() + "线程执行sleep方法");
            TimeUnit.SECONDS.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
