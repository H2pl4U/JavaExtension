package com.h2pl4u.threadlocal;

/**
 * ThreadLocal字面上的意思是局部线程变量，每个线程通过ThreadLocal的get和set方法来访问和修改线程自己独有的变量。
 * 简单地说，ThreadLocal的作用就是为每一个线程提供了一个独立的变量副本，每一个线程都可以独立地改变自己的副本，而
 * 不会影响其它线程所对应的副本。
 * <p>
 * Created by Liuwei on 2020/9/21 16:02
 */
public class ThreadLocalDemo {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    //给ThreadLocal设置初始值
    private static ThreadLocal<String> threadLocal1 = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return "初始值(方式1)";
        }
    };

    private static ThreadLocal<String> threadLocal2 = ThreadLocal.withInitial((() -> "初始值(方式2)"));

    public static void main(String[] args) {
        threadLocal.set("A");
        System.out.println(threadLocal.get());      //A
        threadLocal.remove();
        System.out.println(threadLocal.get());      //null
        System.out.println(threadLocal1.get());     //初始值(方式1)
        System.out.println(threadLocal2.get());     //初始值(方式2)
        threadLocal1.remove();                      //remove无法删除初始值
        System.out.println(threadLocal1.get());     //初始值(方式1)
    }
}
