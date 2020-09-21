package com.h2pl4u.cas;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * AtomicInteger
 * volatile并不能确保线程安全性，要解决文章中提到的累加例子线程安全问题的话，可以使用同步锁（synchronized）和Atomic类型。
 * 但就那个例子来说，使用synchronized同步锁有点小题大作，我们可以选择更为轻量的AtomicInteger来解决
 * Atomic类型内部并没有通过锁来保证线程安全
 * Created by Liuwei on 2020/9/21 14:58
 */
public class AtomicIntegerDemo {
    private static AtomicInteger value = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> IntStream.range(0, 500).forEach(i -> value.incrementAndGet()));
        Thread t2 = new Thread(() -> IntStream.range(0, 500).forEach(i -> value.incrementAndGet()));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(value.get());
    }
}
