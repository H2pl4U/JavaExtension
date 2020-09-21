package com.h2pl4u.juc;

import java.util.concurrent.TimeUnit;

/**
 * volatile修饰的成员变量在线程间具有可见性
 * 通过volatile修饰，对此变量进行写操作时，汇编指令中会有一个LOCK前缀指令，加了这个指令后，
 * 会引发两件事情：
 * 1.将当前处理器缓存行的内容写回到系统内存，也就是强制将工作内存中的值刷回主内存；
 * 2.这个写回到内存的操作会使得在其他CPU里缓存了该内存地址的数据失效。其他CPU缓存数据失效，
 * 则会重新去内存中读取值，也就是被修改的数据。
 * Created by Liuwei on 2020/9/21 13:19
 */
public class VolatileDemo {
    private volatile static int INIT_VALUE = 0;
    private final static int LIMIT = 5;

    public static void main(String[] args) {
        new Thread(() -> {
            int value = INIT_VALUE;
            while (value < LIMIT) {
                if (value != INIT_VALUE) {
                    System.out.println("获取更新后的值:" + INIT_VALUE);
                    value = INIT_VALUE;
                }
            }
        }, "reader").start();

        new Thread(() -> {
            int value = INIT_VALUE;
            while (LIMIT > INIT_VALUE) {
                System.out.println("将值更新为:" + ++value);
                INIT_VALUE = value;
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "writer").start();
    }
}
