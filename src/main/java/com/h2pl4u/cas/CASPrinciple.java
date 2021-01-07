package com.h2pl4u.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS是Compare-And-Swap的缩写，意思为比较并交换。以AtomicInteger为例，其提供了compareAndSet(int expect, int update)方法，
 * expect为期望值（被修改的值在主内存中的期望值），update为修改后的值。compareAndSet方法返回值类型为布尔类型，修改成功则返回true，
 * 修改失败返回false
 * Created by Liuwei on 2020/9/21 15:10
 */
public class CASPrinciple {
    /**
     * 该方法通过调用unsafe类的compareAndSwapInt方法实现相关功能。compareAndSwapInt方法包含四个参数：
     * 1.this，当前对象；
     * 2.valueOffset，value成员变量的内存偏移量（也就是内存地址）
     * 3.期望值
     * 4.更新值
     * <p>
     * 该方法并没有具体Java代码实现，方法通过native关键字修饰。由于Java方法无法直接访问底层系统，Unsafe类相当于一个后门，
     * 可以通过该类的方法直接操作特定内存的数据。Unsafe类存在于sun.msic包中，JVM会帮我们实现出相应的汇编指令。Unsafe类中
     * 的CAS方法是一条CPU并发原语，由若干条指令组成，用于完成某个功能的一个过程。原语的执行必须是连续的，在执行过程中不允许
     * 被中断，不会存在数据不一致的问题。
     * <p>
     * CAS并不是完美的，其存在以下这些缺点：
     * <p>
     * 1.如果刚好while里的CAS操作一直不成功，那么对CPU的开销大；
     * 2.只能确保一个共享变量的原子操作；
     * 3.存在ABA问题。
     *
     * @param args
     */
    public static void main(String[] args) {
        AtomicInteger value = new AtomicInteger(0);
        boolean flag = value.compareAndSet(0, 1);
        System.out.println(flag);   //true
        System.out.println(value.get());    //1
        boolean flag2 = value.compareAndSet(0, 2);
        System.out.println(flag2); //false
        System.out.println(value.get()); //1
    }
}
