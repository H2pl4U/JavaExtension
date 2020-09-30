package com.h2pl4u.designpattern;

/**
 * 桥接模式
 * 将抽象部分和具体实现部分分离，使它们都可以独立变化。通过组合的方式建立两个类之间的关系，而不是通过继承。
 * 适用于：
 * -抽象和实体实现之间增加更多的灵活性；
 * -一个类存在多个独立变化的维度，并且需要独立拓展；
 * -不希望使用继承。
 * 优点：
 * -分离抽象部分和具体实现部分；
 * -提高了系统可拓展性；
 * -符合开闭原则和合成复用原则。
 * 缺点：
 * 增加了系统的理解和设计难度；
 * Created by Liuwei on 2020/9/30 11:44
 */
public class Bridge {
    interface Pie {
        Pie makePie();

        void getType();
    }

    static class CarrotPie implements Pie {
        @Override
        public Pie makePie() {
            System.out.println("制作了🥕🥧");
            return new CarrotPie();
        }

        @Override
        public void getType() {
            System.out.println("蔬菜派");
        }
    }

    static class ApplePie implements Pie {
        @Override
        public Pie makePie() {
            System.out.println("制作了🍎🥧");
            return new ApplePie();
        }

        @Override
        public void getType() {
            System.out.println("水果派");
        }
    }

    static abstract class Store {
        protected Pie pie;

        public Store(Pie pie) {
            this.pie = pie;
        }

        abstract Pie makePie();
    }

    static class SamStore extends Store {

        public SamStore(Pie pie) {
            super(pie);
        }

        @Override
        Pie makePie() {
            System.out.print("山姆大叔的小店💒");
            return pie.makePie();
        }
    }

    static class JackStore extends Store {

        public JackStore(Pie pie) {
            super(pie);
        }

        @Override
        Pie makePie() {
            System.out.print("杰克的小店💒");
            return pie.makePie();
        }
    }

    public static void main(String[] args) {
        Store samStore = new SamStore(new ApplePie());
        Pie samPie = samStore.makePie();
        samPie.getType();

        Store jackStore = new JackStore(new CarrotPie());
        Pie jackPie = jackStore.makePie();
        jackPie.getType();
    }
}
