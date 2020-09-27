package com.h2pl4u.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.IntStream;

/**
 * JDK7提供了一个将任务“分而治之”的框架 — Fork/Join。它把一个大的任务分割成足够小的子任务，如果子任务比较大的话还要对子任务进行继续分割。
 * 分割的子任务分别放到双端队列里，然后启动线程分别从双端队列里获取任务执行。子任务执行完的结果都放在另外一个队列里，启动一个线程从队列里取
 * 数据，然后合并这些数据。
 * RecursiveTask适用于将任务分而治之，并且有返回值的情况，举个计算1到100和的例子
 * Created by Liuwei on 2020/9/27 11:25
 */
public class RecursiveTask {
    //定义最小区间为10
    private final static int MAX_THRESHOLD = 10;

    public static void main(String[] args) {
        final ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> future = forkJoinPool.submit(new CalculateRecursiveTask(1, 100));
        try {
            Integer result = future.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static class CalculateRecursiveTask extends java.util.concurrent.RecursiveTask<Integer> {
        private int start;
        private int end;

        public CalculateRecursiveTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            //如果起始和结束范围小于定义的区间范围则直接计算
            if ((end - start) <= MAX_THRESHOLD) {
                return IntStream.rangeClosed(start, end).sum();
            } else {
                //否则将范围一分为二成两个子任务
                int middle = (start + end) / 2;
                CalculateRecursiveTask leftTask = new CalculateRecursiveTask(start, middle);
                CalculateRecursiveTask rightTask = new CalculateRecursiveTask(middle + 1, end);
                //执行子任务
//                leftTask.fork();
//                rightTask.fork();
                //其实这里执行子任务调用fork方法并不是最佳的选择，最佳的选择是invokeAll方法：
                invokeAll(leftTask, rightTask);
                //汇总子任务
                return leftTask.join() + rightTask.join();

            }
        }
    }
}
