package com.h2pl4u.threadpool;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * newScheduledThreadPool理论是也是可以接收无限个任务，DelayedWorkQueue也是一个无界队列。
 * 使用newScheduledThreadPool创建的线程池除了可以处理普通的Runnable任务外，它还具有调度的功能：
 * 1.延迟指定时间后执行
 * 2.按指定的速率执行
 * 3.按指定的时延执行
 * <p>
 * 对于这些线程池工厂的使用，阿里巴巴编程规范指出：
 * [强制]线程池不允许使用Executors创建,这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险
 * 1)FixedThreadPool和SingleThreadPool:
 * 允许的请求队列长度未Integer.MAX_VALUE,可能会堆积大量的请求，从而导致OOM
 * 2)CachedThreadPool:
 * 允许的创建线程数量未Integer.MAX_VALUE,可能会创建大量的线程，从而导致OOM
 * <p>
 * 因为这几个线程池理论是都可以接收无限个任务，所以这就有内存溢出的风险。
 * 实际上只要我们掌握了ThreadPoolExecutor构造函数7个参数的含义，我们就可以根据不同的业务来创建
 * 出符合需求的线程池。一般线程池的创建可以参考如下规则：
 * - IO密集型任务：IO密集型任务线程并不是一直在执行任务，应该配置尽可能多的线程，线程池线程数量推荐
 * 设置为2 * CPU核心数；对弈IO密集型任务，网络上也有另一种线程池数量计算公式：CPU核心数/(1 - 阻塞系数)，
 * 阻塞系数取值0.8~0.9，至于这两种公式使用哪一个，可以根据实际环境测试比较得出；
 * - 计算密集型任务：此类型需要CPU的大量运算，所以尽可能的去压榨CPU资源，线程池线程数量推荐设置为CPU核心数 + 1。
 * Created by Liuwei on 2020/9/27 10:47
 */
public class NewScheduledThreadPool {

    /**
     * 乍一看，scheduleAtFixedRate和scheduleWithFixedDelay没啥区别，实际它们还是有区别的：
     * - scheduleAtFixedRate按照固定速率执行任务，比如每5秒执行一个任务，即使上一个任务没有结束，5秒后也会开始处理新的任务；
     * - scheduleWithFixedDelay按照固定的时延处理任务，比如每延迟5秒执行一个任务，无论上一个任务处理了1秒，1分钟还是1小时，
     * 下一个任务总是在上一个任务执行完毕后5秒钟后开始执行。
     *
     * @param args
     */
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        System.out.println("ScheduledThreadPool创建完毕");
        //1.延迟指定时间后执行
        scheduledExecutorService.schedule(
                () -> System.out.println("hello"), 5, TimeUnit.SECONDS);
        //2.按指定的速率执行
        scheduledExecutorService.scheduleAtFixedRate(
                () -> System.out.println(LocalTime.now()), 1, 5, TimeUnit.SECONDS);
        //3.按指定的时延执行
        scheduledExecutorService.scheduleWithFixedDelay(
                () -> System.out.println(LocalTime.now()), 1, 5, TimeUnit.SECONDS);
        //获取CPU核心数方法
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println(availableProcessors);
    }

}
