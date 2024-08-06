package com.h2pl4u.juc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2021/4/12 14:07
 *
 * @Author Liuwei
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws Exception {
        // 创建异步执行任务
        CompletableFuture.supplyAsync(CompletableFutureDemo::getPrice)
                //当getPrice()执行完成后，会自动回调thenAccept()中的函数
                .thenAccept(result -> {
                    System.out.println("price: " + result);
                })
                //当出现异常时，会自动回调exceptionally()里函数
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
        TimeUnit.MILLISECONDS.sleep(2000);
    }

    static Double getPrice() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        if (Math.random() < 0.3D) {
            throw new RuntimeException("Error when get price");
        }
        return Math.random() * 20;
    }
}
