package com.h2pl4u.juc;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * CountDownLatch允许一个或多个线程等待其他线程完成操作
 * 其countDown方法用于递减计数器，await方法会使当前线程阻塞，直到计数器递减为0。所以CountDownLatch常用于多个线程之间的协调工作
 * <p>
 * CountDownLatch 和 CyclicBarrier 是 Java 并发包提供的两个非常易用的线程同步工具类，
 * 这两个工具类用法的区别在这里还是有必要再强调一下：CountDownLatch 主要用来解决一个线程
 * 等待多个线程的场景，可以类比旅游团团长要等待所有的游客到齐才能去下一个景点；而CyclicBarrier
 * 是一组线程之间互相等待，更像是几个驴友之间不离不弃。除此之外 CountDownLatch 的计数器是不能
 * 循环利用的，也就是说一旦计数器减到 0，再有线程调用 await()，该线程会直接通过。但CyclicBarrier
 * 的计数器是可以循环利用的，而且具备自动重置的功能，一旦计数器减到 0 会自动重置到你设置的初始值。
 * 除此之外，CyclicBarrier 还可以设置回调函数，可以说是功能丰富。
 * <p>
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
