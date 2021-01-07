package com.h2pl4u.designpattern;

/**
 * 装饰者模式
 * 在不改变原有对象的基础之上，将功能附加到对象上，提供了比继承更有弹性的替代方案。
 * 适用于：
 * -拓展一个类的功能；
 * -动态给对象添加功能，并且动态撤销。
 * 优点：
 * -继承的有力补充，不改变原有对象的情况下给对象拓展功能；
 * -通过使用不同的装饰类、不同的组合方式，实现不同的效果。
 * -符合开闭原则。
 * 缺点：
 * -增加程序复杂性；
 * Created by Liuwei on 2020/9/29 16:04
 */
public class Decorator {
    static abstract class AbstractFruitSalad {
        public abstract String remark();

        public abstract int price();
    }

    static class AbstractDecorator extends AbstractFruitSalad {
        private AbstractFruitSalad fruitSalad;

        public AbstractDecorator(AbstractFruitSalad fruitSalad) {
            this.fruitSalad = fruitSalad;
        }

        @Override
        public String remark() {
            return fruitSalad.remark();
        }

        @Override
        public int price() {
            return fruitSalad.price();
        }
    }

    static class FruitSalad extends AbstractFruitSalad {
        @Override
        public String remark() {
            return "水果🥗（标准） \n";
        }

        @Override
        public int price() {
            return 15;
        }
    }

    static class KiwiDecorator extends AbstractDecorator {

        public KiwiDecorator(AbstractFruitSalad fruitSalad) {
            super(fruitSalad);
        }

        @Override
        public String remark() {
            return super.remark() + "加份🥝（切片） \n";
        }

        @Override
        public int price() {
            return super.price() + 5;
        }
    }

    static class WaterMelonDecorator extends AbstractDecorator {

        public WaterMelonDecorator(AbstractFruitSalad fruitSalad) {
            super(fruitSalad);
        }

        @Override
        public String remark() {
            return super.remark() + "加份🍉(切丁) \n";
        }

        @Override
        public int price() {
            return super.price() + 4;
        }
    }

    /**
     * 通过不同的装饰器自由组合，我们可以灵活的组装出各式各样的水果沙拉，这正是装饰者模式的优点，但明显可以看出代码变复杂了。
     *
     * @param args
     */
    public static void main(String[] args) {
        AbstractFruitSalad fruitSalad = new FruitSalad();
        fruitSalad = new KiwiDecorator(fruitSalad);
        fruitSalad = new WaterMelonDecorator(fruitSalad);
        fruitSalad = new WaterMelonDecorator(fruitSalad);
        System.out.println(fruitSalad.remark() + "价格是：" + fruitSalad.price());
    }
}
