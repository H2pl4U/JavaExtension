package com.h2pl4u.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * CallerRunsPolicy策略：由调用线程处理该任务
 *
 * AbortPolicy策略：丢弃任务，并抛出RejectedExecutionException异常。前面的例子就是使用该策略，所以不再演示。
 * Created by Liuwei on 2020/9/27 9:58
 */
public class CallerRunsPolicy {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2, 3, 10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2), Thread::new,
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        threadPoolExecutor.execute(new longTask("任务1"));
        threadPoolExecutor.execute(new longTask("任务2"));
        threadPoolExecutor.execute(new longTask("任务3"));
        threadPoolExecutor.execute(new longTask("任务4"));
        threadPoolExecutor.execute(new longTask("任务5"));

        //线程池最多只能一次性提交4个任务，第5个任务提交后会被拒绝策略处理。
        threadPoolExecutor.shutdown();
    }

    private static class shortTask implements Runnable {
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

    private static class longTask implements Runnable {
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
