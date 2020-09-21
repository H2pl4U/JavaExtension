package com.h2pl4u.juc;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * JUC中的Exchanger允许成对的线程在指定的同步点上通过exchange方法来交换数据。
 * 如果第一个线程先执行exchange方法，它会一直等待第二个线程也 执行exchange方法，
 * 当两个线程都到达同步点时，这两个线程就可以交换数据，将当前线程生产 出来的数据传递给对方。
 * (通过Exchanger交换的是同一个对象，而不是对象的拷贝)
 * Created by Liuwei on 2020/9/21 11:46
 */
public class ExchangerDemo {
    public static void main(String[] args) {
        final Exchanger<String> exchanger = new Exchanger<>();

        new Thread(() -> {
            System.out.println("t1 start");
            try {
                String exchange = exchanger.exchange("来自t1的数据");
                System.out.println("接收t2发送的数据:" + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t1 end");
        }, "t1").start();

        new Thread(() -> {
            System.out.println("t2 start");
            try {
                //t1也会进入等待，直到双方都准备号交换数据
                TimeUnit.SECONDS.sleep(3);
                String exchange = exchanger.exchange("来自t2的数据");
                System.out.println("接收t1发送的数据:" + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2 end");
        }, "t2").start();
    }
}
