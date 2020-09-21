package com.h2pl4u.juc;

import java.util.stream.IntStream;

/**
 * volatile 无法满足原子性，线程不安全 通过Atomic类能够解决该问题
 * Created by Liuwei on 2020/9/21 13:37
 */
public class VolatileDemo3 {
    private volatile static int value;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> IntStream.range(0, 500).forEach(i -> value += 1));
        Thread t2 = new Thread(() -> IntStream.range(0, 500).forEach(i -> value += 1));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(value);
    }
}
