package com.h2pl4u.safecollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

/**
 * 常用的集合类型如ArrayList，HashMap，HashSet等，在并发环境下修改操作都是线程不安全的，
 * 会抛出java.util.ConcurrentModificationException异常，这节主要记录如何在并发环境
 * 下安全地修改集合数据
 * 这三种解决办法中，更推荐使用CopyOnWriteArrayList
 * Created by Liuwei on 2020/9/22 9:44
 */
public class SafeListDemo {
    public static void main(String[] args) {
        unsafeListFunc();
        vectorFunc();
        synchronizedListFunc();
        copyOnWriteListFunc();
    }

    /**
     * 查看CopyOnWriteArrayList类的add方法源码，会发现它是通过可重入锁来解决线程安全问题的
     * 上面方法的源码使用了写时复制的思想（CopyOnWrite）：往一个容器添加元素的时候，不直接往当前容器Object[]添加，
     * 而是先将当前容器Object[]进行复制，复制出一个新的容器Object[] newElements，然后往新的容器Object[] newElements
     * 里添加新的元素。添加完后，再将原容器的引用指向新的容器（即源码中的setArray(newElements)）。
     * 这种做法的好处是可以对CopyOnWrite的容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。
     * 所以这也是一种读写分离的思想，即读和写的容器是不同的容器。
     */
    private static void copyOnWriteListFunc() {
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        IntStream.range(0, 30).forEach(i -> new Thread(() -> list.add(String.valueOf(i)), String.valueOf(i)).start());
    }

    /**
     * 使用Collections工具类的synchronizedList方法
     * 其本质还是通过同步锁来解决线程安全问题
     */
    private static void synchronizedListFunc() {
        List<String> list = new ArrayList<>();
        List<String> safeList = Collections.synchronizedList(list);
        IntStream.range(0, 30).forEach(i -> new Thread(() -> safeList.add(String.valueOf(i)), String.valueOf(i)).start());
    }

    /**
     * Vector类的add方法是通过加同步锁来实现线程安全的
     */
    private static void vectorFunc() {
        Vector<String> list = new Vector<>();
        IntStream.range(0, 30).forEach(i -> new Thread(() -> list.add(String.valueOf(i)), String.valueOf(i)).start());
    }

    /**
     * 举个ArrayList线程不安全的例子:
     * 有30个线程对data进行修改操纵，多运行几次上面的程序，会抛出java.util.ConcurrentModificationException异常
     * 因为多个线程并发争抢修改data导致，当一个线程正在写入但还未写完时，另一个线程抢夺写入，这便导致了数据异常
     */
    private static void unsafeListFunc() {
        List<String> list = new ArrayList<>();
        IntStream.range(0, 30).forEach(i -> new Thread(() -> list.add(String.valueOf(i)), String.valueOf(i)).start());
    }
}
