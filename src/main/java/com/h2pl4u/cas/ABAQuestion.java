package com.h2pl4u.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA问题
 * CAS实现的一个重要前提是需要取出某一时刻的数据并在当下时刻比较交换，这之间的时间差会导致数据的变化。
 * 比如：thread1线程从主内存中取出了变量a的值为A，thread2页从主内存中取出了变量a的值为A。由于线程
 * 调度的不确定性，这时候thread1可能被短暂挂起了，thread2进行了一些操作将值修改为了B，然后又进行了
 * 一些操作将值修改回了A，这时候当thread1重新获取CPU时间片重新执行CAS操作时，会发现变量a在主内存中
 * 的值任然是A，所以CAS操作成功。
 * <p>
 * 那么如何解决CAS的ABA问题呢？光通过判断值是否相等并不能确保在一定时间差内值没有变更过，所以需要一个
 * 额外的指标来辅助判断，类似于时间戳，版本号等
 * JUC提供了一个AtomicStampedReference类，通过构造方法可以看出，除了指定初始值外，还需指定一个版本号（戳）
 * Created by Liuwei on 2020/9/21 15:23
 */
public class ABAQuestion {
    public static void main(String[] args) {
        abaQuestion();
        solveABAQuestion();
    }

    /**
     * 使用AtomicStampedReference解决ABA问题
     */
    private static void solveABAQuestion() {
        //初始值为A 版本号为1
        AtomicStampedReference<String> reference = new AtomicStampedReference<>("A", 1);
        new Thread(() -> {
            int stamp = reference.getStamp();
            System.out.println(Thread.currentThread().getName() + "当前版本号为:" + stamp);
            //休眠1秒 让t2也拿到初始的版本号
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //模拟一次ABA操作
            reference.compareAndSet("A", "B", reference.getStamp(), reference.getStamp() + 1);
            reference.compareAndSet("B", "A", reference.getStamp(), reference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "线程完成了一次ABA操作");
        }, "t1").start();

        new Thread(() -> {
            int stamp = reference.getStamp();
            System.out.println(Thread.currentThread().getName() + "当前版本号为:" + stamp);
            //让t2休眠2s 确保t1的ABA操作完成
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result = reference.compareAndSet("A", "B", stamp, stamp + 1);
            if (result) {
                System.out.println(Thread.currentThread().getName() + "线程修改值成功，当前值为：" + reference.getReference());
            } else {
                System.out.println(Thread.currentThread().getName() + "线程修改值失败，当前值为：" + reference.getReference() + "，版本号为：" + reference.getStamp());
            }
        }, "t2").start();
    }

    /**
     * 模拟ABA操作
     */
    private static void abaQuestion() {
        AtomicReference<String> atomicReference = new AtomicReference<>("A");
        new Thread(() -> {
            //模拟ABA操作
            atomicReference.compareAndSet("A", "B");
            atomicReference.compareAndSet("B", "A");
            System.out.println(Thread.currentThread().getName() + "线程完成了一个ABA操作");
        }, "t1").start();

        new Thread(() -> {
            //让t2睡眠2s 确保t1的ABA操作完成
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result = atomicReference.compareAndSet("A", "B");
            if (result) {
                System.out.println(Thread.currentThread().getName() + "线程修改值成功，当前值为：" + atomicReference.get());
            }
        }, "t2").start();
    }


}
