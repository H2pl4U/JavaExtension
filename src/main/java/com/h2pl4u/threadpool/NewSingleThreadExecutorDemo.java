package com.h2pl4u.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 核心线程数和最大线程数都为1，每次只能有一个线程处理任务。
 * LinkedBlockingQueue队列可以接收无限个新任务。
 * Created by Liuwei on 2020/9/27 10:45
 */
public class NewSingleThreadExecutorDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        System.out.println("核心线程数和最大线程数都为1的线程池创建完毕");
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
