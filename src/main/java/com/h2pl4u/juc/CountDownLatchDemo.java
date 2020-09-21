package com.h2pl4u.juc;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * CountDownLatch允许一个或多个线程等待其他线程完成操作
 * 其countDown方法用于递减计数器，await方法会使当前线程阻塞，直到计数器递减为0。所以CountDownLatch常用于多个线程之间的协调工作
 * Created by Liuwei on 2020/9/21 11:06
 */
public class CountDownLatchDemo {
    private static ExecutorService ExecutorService = Executors.newFixedThreadPool(2);
    private static CountDownLatch latch = new CountDownLatch(10);

    public static void main(String[] args) throws InterruptedException {
        //1.模拟从数据库获取数据
        int[] data = query();
        System.out.println("获取数据完毕");
        //2.处理数据
        IntStream.range(0, data.length).forEach(i -> {
            ExecutorService.execute(() -> {
                System.out.println(Thread.currentThread() + "处理第" + (i + 1) + "条数据");
                int value = data[i];
                if (value % 2 == 0) {
                    data[i] = value * 2;
                } else {
                    data[i] = value * 10;
                }
                latch.countDown();
            });
        });
        latch.await();
        System.out.println("数据处理完毕");
        //关闭线程池
        ExecutorService.shutdown();
        //保存数据
        save(data);
    }

    private static void save(int[] data) {
        System.out.println("保存数据 - " + Arrays.toString(data));
    }

    private static int[] query() {
        return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    }
}
