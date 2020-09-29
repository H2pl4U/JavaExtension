package com.h2pl4u.designpattern;

/**
 * 工厂方法模式
 * 为了解决简单工厂模式的缺点，诞生了工厂方法模式（Factory method pattern）。
 * 定义：定义创建对象的接口，让实现这个接口的类来决定实例化哪个类，工厂方法让类的实例化推迟到了子类进行。
 * 优点：具体产品从客户端代码中抽离出来，解耦。加入新的类型时，只需添加新的工厂方法（无需修改旧的工厂方法代码），符合开闭原则。
 * 缺点：类的个数容易过多，增加复杂度。
 * Created by Liuwei on 2020/9/29 11:38
 */
public class FactoryMethod {
    static abstract class Fruit {
        abstract void eat();
    }

    static class Banana extends Fruit {
        @Override
        void eat() {
            System.out.println("恰🍌");
        }
    }

    static abstract class FruitFactory {
        abstract Fruit produceFruit(String name);
    }

    static class AppleFruitFactory extends FruitFactory {
        @Override
        Fruit produceFruit(String name) {
            if (name.equals("banana")) {
                return new Banana();
            }
            return null;
        }
    }

    public static void main(String[] args) {
        FruitFactory fruitFactory = new AppleFruitFactory();
        Fruit banana = fruitFactory.produceFruit("banana");
        banana.eat();   //恰🍌
    }
}
