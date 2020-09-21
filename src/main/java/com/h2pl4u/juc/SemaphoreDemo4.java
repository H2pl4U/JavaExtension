package com.h2pl4u.juc;

import java.util.Collection;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 别的API
 * hasQueuedThreads方法用于判断是否有处于等待获取许可证状态的线程；
 * getQueueLength用于获取处于等待获取许可证状态的线程的数量；getQueuedThreads用于
 * 获取处于等待获取许可证状态的线程集合。
 * Created by Liuwei on 2020/9/21 14:32
 */
public class SemaphoreDemo4 {
    public static void main(String[] args) {
        //定义许可证数量
        final MySemaphore semaphore = new MySemaphore(1);
        IntStream.range(0, 4).forEach(i -> {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "开始");
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "获取许可证");
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(Thread.currentThread().getName() + "释放许可证");
                    semaphore.release();
                }
                System.out.println(Thread.currentThread().getName() + "结束");
            }, "t" + (i + 1)).start();
        });
        while (true) {
            if (semaphore.hasQueuedThreads()) {
                System.out.println("等待线程数量：" + semaphore.getQueueLength());
                Collection<Thread> queuedThreads = semaphore.getQueuedThreads();
                System.out.println("等待线程：" + queuedThreads.stream().map(Thread::getName).collect(Collectors.joining(",")));
            }
        }

    }

    /**
     * getQueuedThreads是protected的，所以要使用它，需要自定义一个Semaphore的子类：
     */
    static class MySemaphore extends Semaphore {
        private static final long serialVersionUID = -2595494765642942297L;

        public MySemaphore(int permits) {
            super(permits);
        }

        public MySemaphore(int permits, boolean fair) {
            super(permits, fair);
        }

        @Override
        protected Collection<Thread> getQueuedThreads() {
            return super.getQueuedThreads();
        }
    }
}
