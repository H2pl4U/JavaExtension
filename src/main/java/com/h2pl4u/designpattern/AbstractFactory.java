package com.h2pl4u.designpattern;

/**
 * 抽象工厂模式（Abstract factory pattern）
 * 提供了一系列相关或者相互依赖的对象的接口，关键字是“一系列”。
 * 优点：具体产品从客户端代码中抽离出来，解耦。将一个系列的产品族统一到一起创建。
 * 缺点：拓展新的功能困难，需要修改抽象工厂的接口；
 * 综上所述，抽象工厂模式适合那些功能相对固定的产品族的创建。
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
