package com.h2pl4u.designpattern;

/**
 * 迭代器模式
 * 迭代器模式（Iterator Pattern）是 Java 和 .Net 编程环境中非常常用的设计模式。
 * 这种模式用于顺序访问集合对象的元素，不需要知道集合对象的底层表示。
 * 迭代器模式属于行为型模式。
 * 介绍
 * 意图：提供一种方法顺序访问一个聚合对象中各个元素, 而又无须暴露该对象的内部表示。
 * 主要解决：不同的方式来遍历整个整合对象。
 * 何时使用：遍历一个聚合对象。
 * 如何解决：把在元素之间游走的责任交给迭代器，而不是聚合对象。
 * 关键代码：定义接口：hasNext, next。
 * 应用实例：JAVA 中的 iterator。
 * 优点： 1、它支持以不同的方式遍历一个聚合对象。 2、迭代器简化了聚合类。
 * 3、在同一个聚合上可以有多个遍历。 4、在迭代器模式中，增加新的聚合类和迭代器类都很方便，无须修改原有代码。
 * 缺点：由于迭代器模式将存储数据和遍历数据的职责分离，增加新的聚合类需要对应增加新的迭代器类，类的个数成对增加，这在一定程度上增加了系统的复杂性。
 * Created by Liuwei on 2020/10/21 13:58
 */
public class IteratorPattern {
    public interface Iterator {
        boolean hasNext();
        Object next();
    }

    public interface Container {
        public Iterator getIterator();
    }

    public static class NameRepository implements Container {
        public String[] names = {"Robert", "John", "Julie", "Lora"};
        @Override
        public Iterator getIterator() {
            return new NameIterator();
        }

        private class NameIterator implements Iterator {
            int index;
            @Override
            public boolean hasNext() {
                if (index < names.length) {
                    return true;
                }
                return false;
            }

            @Override
            public Object next() {
                if (this.hasNext()) {
                    return names[index++];
                }
                return null;
            }
        }
    }

    public static void main(String[] args) {
        NameRepository nameRepository = new NameRepository();
        for (Iterator iterator = nameRepository.getIterator(); iterator.hasNext();) {
            String name = (String) iterator.next();
            System.out.println("Name:" + name);
        }
    }
}
