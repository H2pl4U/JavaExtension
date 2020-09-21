package com.h2pl4u.juc;

import java.util.concurrent.Exchanger;

/**
 * 那么如果线程不成对会出现什么情况呢？我们添加thread3线程
 * 可看到thread1和thread3交换了数据然后正常停止了，而thread2由于没有线程和
 * 它交换数据而苦苦等待，线程永远不会停止
 * Created by Liuwei on 2020/9/21 11:56
 */
public class ExchangerDemo2 {
    public static void main(String[] args) {
        final Exchanger<String> exchanger = new Exchanger<>();

        new Thread(() -> {
            System.out.println("t1 start");
            try {
                String exchange = exchanger.exchange("发送数据-t1");
                System.out.println("接收数据:" + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t1 end");
        }, "t1").start();

        new Thread(() -> {
            System.out.println("t2 start");
            try {
                String exchange = exchanger.exchange("发送数据-t2");
                System.out.println("接收数据:" + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2 end");
        }, "t2").start();

        new Thread(() -> {
            System.out.println("t3 start");
            try {
                String exchange = exchanger.exchange("发送数据-t3");
                System.out.println("接收数据:" + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t3 end");
        }, "t3").start();
    }
}
