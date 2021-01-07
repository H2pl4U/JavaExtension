package com.h2pl4u.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 由于线程的创建和销毁都需要消耗一定的CPU资源，所以在高并发下这种创建线程的方式将严重影响代码执行效率。
 * 而线程池的作用就是让一个线程执行结束后不马上销毁，继续执行新的任务，这样就节省了不断创建线程和销毁线程的开销。
 * Created by Liuwei on 2020/9/27 9:19
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1, 2, 10,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1),
                Thread::new, new ThreadPoolExecutor.AbortPolicy());
        System.out.println("线程池创建完毕");
        //通过execute方法向线程池提交1个任务
        threadPoolExecutor.execute(() -> sleep(100)); //队列线程个数 0
        //通过execute方法向线程池提交2个任务
        threadPoolExecutor.execute(() -> sleep(5)); //队列线程个数 1
        //通过execute方法向线程池提交3个任务
        threadPoolExecutor.execute(() -> sleep(5)); //队列线程个数 1
        //通过execute方法向线程池提交4个任务  设置的拒绝策略为AbortPolicy，所以最后提交的那个任务直接被拒绝了
//        threadPoolExecutor.execute(()-> sleep(5)); //RejectedExecutionException

        int activeCount = -1;
        int queueSize = -1;
        while (true) {
            if (activeCount != threadPoolExecutor.getActiveCount()
                    && queueSize != threadPoolExecutor.getQueue().size()) {
                System.out.println("活跃线程个数 " + threadPoolExecutor.getActiveCount());
                System.out.println("核心线程个数 " + threadPoolExecutor.getCorePoolSize());
                System.out.println("队列线程个数 " + threadPoolExecutor.getQueue().size());
                System.out.println("最大线程数 " + threadPoolExecutor.getMaximumPoolSize());
                System.out.println("------------------------------------");
                activeCount = threadPoolExecutor.getActiveCount();
                queueSize = threadPoolExecutor.getQueue().size();
            }
        }
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
