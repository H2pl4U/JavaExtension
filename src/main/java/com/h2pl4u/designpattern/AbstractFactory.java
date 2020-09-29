package com.h2pl4u.designpattern;

/**
 * Created by Liuwei on 2020/9/29 11:44
 */
public class AbstractFactory {
    static abstract class Price {
        abstract void pay();
    }

    static abstract class Fruit {
        abstract void buy();
    }

    static class ApplePrice extends Price {
        @Override
        void pay() {
            System.out.println("支付了10块钱");
        }
    }

    static class AppleFruit extends Fruit {
        @Override
        void buy() {
            System.out.println("购买了2个苹果");
        }
    }

    static abstract class FruitFactory {
        abstract Price getPrice();
        abstract Fruit getFruit();
    }

    static class AppleFruitFactory extends FruitFactory {
        @Override
        Price getPrice() {
            return new ApplePrice();
        }

        @Override
        Fruit getFruit() {
            return new AppleFruit();
        }
    }

    public static void main(String[] args) {
        FruitFactory fruitFactory = new AppleFruitFactory();
        fruitFactory.getPrice().pay();
        fruitFactory.getFruit().buy();
    }
}
