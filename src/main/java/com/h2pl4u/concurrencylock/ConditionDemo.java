package com.h2pl4u.concurrencylock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition接口提供了类似Object的wait、notify和notifyAll方法，
 * 与Lock配合可以实现生产/消费模式，但是这两者在使用方式以及功能特性上还是有差别的
 * 使用Condition实现一个生产消费的例子
 * Created by Liuwei on 2020/9/21 18:25
 */
public class ConditionDemo {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    //初始数据
    private static int data = 0;
    //是否被消费
    private static volatile boolean consumed = false;

    /**
     * 通过consumed判断数据是否被消费。produceData方法在获取锁后，判断数据是否被消费，
     * 如果没有被消费，则调用Condition的await方法进入等待，直到Condition对象的signal
     * 方法被调用；consumeData方法逻辑和produceData一致。
     * Condition核心用法就是通过await方法让线程进入阻塞等待状态，通过signal解除阻塞状态。
     * @param args
     */
    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                produceData();
            }
        }, "producer").start();
        new Thread(() -> {
            while (true) {
                consumeData();
            }
        },"consumer").start();
    }

    private static void consumeData() {
        try {
            lock.lock(); //获取锁
            while (consumed) {
                condition.await();
            }
            TimeUnit.SECONDS.sleep(1);
            System.out.println(Thread.currentThread().getName() + " consume data = " + data);
            consumed = true;    //消费完将消费标识置为true
            condition.signal(); //解除await 用于通知生产者可以开始生产了
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();  //释放锁
        }
    }

    private static void produceData() {
        try {
            lock.lock();    //获取锁
            while (!consumed) { //判断数据是否被消费
                condition.await(); //如果没有被消费则进入等待
            }
            TimeUnit.SECONDS.sleep(1);
            data++;
            System.out.println(Thread.currentThread().getName() + " produce data = " + data);
            consumed = false;   //生成完数据将消费标识置为false
            condition.signal(); //解除await 用于通知消费者可以开始消费了
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
