package com.h2pl4u.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * DiscardOldestPolicy策略：丢弃最早被放入到线程队列的任务，将新提交的任务放入到线程队列末端
 * Created by Liuwei on 2020/9/27 10:08
 */
public class DiscardOldestPolicy {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2, 3, 10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), (ThreadFactory) Thread::new,
                new ThreadPoolExecutor.DiscardOldestPolicy());

        threadPoolExecutor.execute(new shortTask("任务1"));
        threadPoolExecutor.execute(new longTask("任务2"));
        threadPoolExecutor.execute(new longTask("任务3"));
        threadPoolExecutor.execute(new shortTask("任务4"));
        threadPoolExecutor.execute(new shortTask("任务5"));

        //最后提交的任务被执行了，而第3个任务是第一个被放到线程队列的任务，被丢弃了。
        threadPoolExecutor.shutdown();
    }

    static class shortTask implements Runnable {

        private String name;

        public shortTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "执行shortTask-name-" + name + "完毕");
            } catch (InterruptedException e) {
                System.err.println("shortTask执行过程中被打断" + e.getMessage());
            }
        }
    }

    static class longTask implements Runnable {

        private String name;

        public longTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "执行longTask-name-" + name + "完毕");
            } catch (InterruptedException e) {
                System.err.println("longTask执行过程中被打断" + e.getMessage());
            }
        }
    }
}
