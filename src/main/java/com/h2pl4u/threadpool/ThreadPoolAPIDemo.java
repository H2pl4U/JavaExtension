package com.h2pl4u.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExecutor提供了几个判断线程池状态的方法
 * Created by Liuwei on 2020/9/27 11:06
 */
public class ThreadPoolAPIDemo {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1, 2, 5,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(2),
                Thread::new, new ThreadPoolExecutor.AbortPolicy());

        threadPoolExecutor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPoolExecutor.shutdown();
        System.out.println("线程池为shutdown状态：" + threadPoolExecutor.isShutdown());
        System.out.println("线程池正在关闭：" + threadPoolExecutor.isTerminating());
        System.out.println("线程池已经关闭：" + threadPoolExecutor.isTerminated());
        threadPoolExecutor.awaitTermination(6, TimeUnit.SECONDS);
        System.out.println("线程池已经关闭：" + threadPoolExecutor.isTerminated());

        //线程池核心线程即使是空闲状态也不会被销毁，除非使用allowCoreThreadTimeOut设置了允许核心线程超时
        threadPoolExecutor.allowCoreThreadTimeOut(true);

        //ThreadPoolExecutor提供了一remove方法,它删除的是线程队列中的任务，而非正在被执行的任务
        Runnable r = () -> System.out.println("看看我是否会被删除");
        threadPoolExecutor.execute(r);
        threadPoolExecutor.remove(r);

        threadPoolExecutor.shutdown();

    }
}
