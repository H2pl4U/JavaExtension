package com.h2pl4u.juc;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 设置超时时间
 * 如果不想线程在交换数据的时候等待过长的时间，我们可以使用exchanger的重载
 * 方法exchange(V x, long timeout, TimeUnit unit)来指定超时时间
 * Created by Liuwei on 2020/9/21 12:00
 */
public class ExchangerDemo3 {
    public static void main(String[] args) {
        final Exchanger<String> exchanger = new Exchanger<>();
        new Thread(() -> {
            System.out.println("t1 start");
            try {
                String exchange = exchanger.exchange("来自t1的数据", 5, TimeUnit.SECONDS);
                System.out.println("接收t2发送的数据:" + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            System.out.println("t1 end");
        }, "t1").start();

        new Thread(() -> {
            System.out.println("t2 start");
            try {
                TimeUnit.SECONDS.sleep(10);
                String exchange = exchanger.exchange("来自t2的数据");
                System.out.println("接收t1发送的数据:" + exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2 end");
        }, "t2").start();
    }
}
