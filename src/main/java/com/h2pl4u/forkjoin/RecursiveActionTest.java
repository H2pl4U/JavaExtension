package com.h2pl4u.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 使用方式和RecursiveTask类似，只不过没有返回值
 * 上面只是为了演示Fork/Join的用法，实际是采用这种方式计算反而更加费时，因为切割任务，分配线程需要额外的开销。
 * 其实什么时候用不必太纠结，一个足够大的任务，如果采用Fork/Join来处理比传统处理方式快的话，那就毫不犹豫的选择它吧！
 * Created by Liuwei on 2020/9/27 11:39
 */
public class RecursiveActionTest {
    //定义最小区间为10
    private final static int MAX_THRESHOLD = 10;
    private final static AtomicInteger SUM = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        final ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(new CalculateRecursiveAction(0, 100));
        forkJoinPool.awaitTermination(2, TimeUnit.SECONDS);
        System.out.println(SUM);
    }

    private static class CalculateRecursiveAction extends RecursiveAction {
        private int start;
        private int end;

        public CalculateRecursiveAction(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            // 如果起始和结束范围小于定义的区间范围，则直接计算
            if ((end - start) <= MAX_THRESHOLD) {
                SUM.addAndGet(IntStream.rangeClosed(start, end).sum());
            } else {
                // 否则，将范围一分为二，分成两个子任务
                int middle = (end + start) / 2;
                CalculateRecursiveAction leftAction = new CalculateRecursiveAction(start, middle);
                CalculateRecursiveAction rightAction = new CalculateRecursiveAction(middle + 1, end);
                //执行子任务
                invokeAll(leftAction, rightAction);
                // 没有汇总子任务结果过程，因为没有返回值
            }
        }
    }
}
