package com.h2pl4u.threadpool;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Liuwei on 2020/9/27 9:51
 */
public class ThreadPoolShutdownNowDemo {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2, 4, 10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2), Thread::new,
                new ThreadPoolExecutor.AbortPolicy()
        );
        threadPoolExecutor.execute(new shortTask());
        threadPoolExecutor.execute(new longTask());
        threadPoolExecutor.execute(new longTask());
        threadPoolExecutor.execute(new shortTask());

        List<Runnable> runnables = threadPoolExecutor.shutdownNow(); // 马上关闭，并返回还未被执行的任务
        System.out.println(runnables);

        System.out.println("已经执行了线程池shutdownNow方法");

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
