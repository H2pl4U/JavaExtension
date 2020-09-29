package com.h2pl4u.designpattern;

/**
 * 简单工厂模式
 * 简单工厂模式严格意义上来说，并不属于设计模式中的一种，不过这里还是简单记录下。
 * 定义：由一个工厂对象决定创建出哪一种类型实例。客户端只需传入工厂类的参数，无心关心创建过程。
 * 优点：具体产品从客户端代码中抽离出来，解耦。
 * 缺点：工厂类职责过重，增加新的类型时，得修改工程类得代码，违背开闭原则。
 * Created by Liuwei on 2020/9/29 11:30
 */
public class SimpleFactory {
    static abstract class Fruit {
        abstract void eat();
    }

    static class Apple extends Fruit {
        @Override
        void eat() {
            System.out.println("恰🍎");
        }
    }

    static class FruitFactory {
        public Fruit produce(String name) {
            if (name.equals("apple")) {
                return new Apple();
            } else {
                return null;
            }
        }
    }

    public static void main(String[] args) {
        FruitFactory fruitFactory = new FruitFactory();
        Fruit apple = fruitFactory.produce("apple");
        apple.eat();    //恰🍎
    }
}
