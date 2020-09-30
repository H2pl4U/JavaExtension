package com.h2pl4u.designpattern;

/**
 * 模板方法模式
 * 模板方法模式定义了一个流程的骨架，由多个方法组成。并允许子类为一个或多个步骤提供实现。
 * 简而言之就是公共的不变的部分由父类统一实现，变化的部分由子类来个性化实现。
 * 优点：
 * -提高复用性；
 * -提高拓展性；
 * -符合开闭原则。
 * 缺点：
 * -类的数目增加；
 * -增加了系统实现的复杂度；
 * -父类添加新的抽象方法，所有子类都要改一遍。
 * Created by Liuwei on 2020/9/30 14:08
 */
public class Template {
    static abstract class Takeaway {
        final void order() {
            System.out.println("下单");
        }

        final void packageSend() {
            System.out.println("打包派送");
        }

        protected abstract void make();

        protected boolean needTableware() {
            return true;
        }

        final void flow() {
            this.order();
            this.make();
            if (needTableware()) {
                System.out.println("赠送一次性餐具");
            }
            this.packageSend();
        }
    }

    static class BarbecueTakeaway extends Takeaway {
        private final boolean needTableware;

        public BarbecueTakeaway(boolean needTableware) {
            this.needTableware = needTableware;
        }

        @Override
        protected void make() {
            System.out.println("制作烤肉");
        }

        @Override
        protected boolean needTableware() {
            return this.needTableware;
        }
    }

    static class FruitTakeaway extends Takeaway {

        @Override
        protected void make() {
            System.out.println("水果配货");
        }

        @Override
        protected boolean needTableware() {
            return false;
        }
    }

    public static void main(String[] args) {
        Takeaway barbecue = new BarbecueTakeaway(true);
        barbecue.flow();

        FruitTakeaway fruit = new FruitTakeaway();
        fruit.flow();
    }
}
