package com.h2pl4u.designpattern;

/**
 * 将一个类的接口转换为期望的另一个接口，使原本不兼容的类可以一起工作。
 * 适用于：
 * 已存在的类，它的方法和需求不匹配时（方法结果相同或者相似）
 * 优点:
 * -提高类的透明性和复用，现有的类复用但不需改变；
 * -目标类和适配器类解耦，提高程序拓展性；
 * -符合开闭原则。
 * 缺点：
 * -适配器编写过程需要全面考虑，可能会增加系统的复杂性；
 * -降低代码可读性。
 * <p>
 * 分为：类适配器模式和对象适配器模式。
 * Created by Liuwei on 2020/9/29 16:23
 */
public class Adapter {
    static class Apple {
        public void addApple() {
            System.out.println("添加了🍎");
        }
    }

    interface Pie {
        void make();
    }

    static class ApplePieAdaptor extends Apple implements Pie {
        //类适配器模式
//        @Override
//        public void make() {
//            System.out.println("制作一个派🥧");
//            super.addApple();
//        }

        //对象适配器模式
        private Apple apple = new Apple();

        @Override
        public void make() {
            System.out.println("制作一个派🥧");
            apple.addApple();
        }
    }

    public static void main(String[] args) {
        Pie pie = new ApplePieAdaptor();
        pie.make();
    }
}
