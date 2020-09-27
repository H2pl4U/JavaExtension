package com.h2pl4u.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 通过newFixedThreadPool创建的是一个固定大小的线程池，大小由nThreads参数指定，它具有如下几个特点:
 * 1.因为corePoolSize和maximumPoolSize的值都为nThreads，所以线程池中线程数量永远等于nThreads，
 * 不可能新建除了核心线程数的线程来处理任务，即keepAliveTime实际上在这里是无效的。
 * 2.LinkedBlockingQueue是一个无界队列（最大长度为Integer.MAX_VALUE），所以这个线程池理论是可
 * 以无限的接收新的任务，这就是为什么上面没有指定拒绝策略的原因。
 * Created by Liuwei on 2020/9/27 10:35
 */
public class NewFixedThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        System.out.println("固定大小的线程池创建完毕");
        //通过execute方法向线程池提交1个任务
        executorService.execute(() -> sleep(100));
        //通过execute方法向线程池提交2个任务
        executorService.execute(() -> sleep(100));
        //通过execute方法向线程池提交3个任务
        executorService.execute(() -> sleep(100));
        //通过execute方法向线程池提交4个任务  设置的拒绝策略为AbortPolicy，所以最后提交的那个任务直接被拒绝了
//        threadPoolExecutor.execute(()-> sleep(5)); //RejectedExecutionException
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
