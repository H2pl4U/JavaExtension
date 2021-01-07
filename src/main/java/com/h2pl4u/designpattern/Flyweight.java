package com.h2pl4u.designpattern;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 享元模式
 * 提供了减少对象数量从而改善应用所需的对象结构的方式，运用共享技术有效地支持大量细粒度的对象。
 * 适用于：
 * -底层系统开发，解决性能问题；
 * -系统拥有大量相似对象，需要缓冲池的场景。
 * 优点：
 * 减少对象的创建，降低内存占用；
 * 缺点：
 * -关注内/外部状态，关注线程安全问题；
 * -程序的逻辑复杂化。
 * 内部状态：简单理解为享元对象的属性状态，不会因为外部的改变而改变； 外部状态：简单理解为方法参数。
 * <p>
 * Created by Liuwei on 2020/9/29 16:46
 */
public class Flyweight {
    interface Pie {
        void make();
    }

    static class FruitPie implements Pie {
        private String name;
        private LocalDateTime time;

        public FruitPie(String name) {
            this.name = name;
        }

        public void setTime(LocalDateTime time) {
            this.time = time;
        }

        @Override
        public void make() {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(name + "生产时间：" + this.time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class FruitPieFactory {
        private static final HashMap<String, FruitPie> PIE_HASH_MAP = new HashMap<>();

        public static FruitPie produce(String name) {
            FruitPie fruitPie = PIE_HASH_MAP.get(name);
            if (fruitPie == null) {
                System.out.println("没有" + name + "制作方法，学习制作...");
                fruitPie = new FruitPie(name);
                PIE_HASH_MAP.put(name, fruitPie);
            }
            return fruitPie;
        }
    }

    private static final String[] PIE = {"🍇派", "🍈派", "🍓派", "🍒派"};

    /**
     * 从结果看，在10次循环中，只生产了4个对象，这很好的描述了系统有大量相似对象，需要缓冲池的场景
     *
     * @param args
     */
    public static void main(String[] args) {
        IntStream.range(0, 10).forEach(i -> {
            String name = PIE[(int) (Math.random() * PIE.length)];
            FruitPie fruitPie = FruitPieFactory.produce(name);
            fruitPie.setTime(LocalDateTime.now());
            fruitPie.make();
        });
    }
}
