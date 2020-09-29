package com.h2pl4u.designpattern;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;

/**
 * Created by Liuwei on 2020/9/29 14:51
 */
public class Singleton {

    /**
     * 懒汉模式下的单例写法是最简单的，但它是线程不安全的
     */
    static class LazySingleton {
        private static LazySingleton lazySingleton = null;

        private LazySingleton() {
        }

        public LazySingleton getInstance() {
            if (lazySingleton == null) {
                return new LazySingleton();
            }
            return lazySingleton;
        }
    }

    /**
     * 懒汉单例模式
     * 可加同步锁解决线程安全问题
     * 但是同步锁锁的是整个类，比较消耗资源，并且即使运行内存中已经存在LazySingleton，
     * 调用其getInstance还是会上锁，所以这种写法也不是很好
     */
    static class LazySingletonEx {
        private static LazySingletonEx lazySingletonEx = null;

        private LazySingletonEx() {
        }

        public LazySingletonEx getInstance() {
            synchronized (this) {
                if (lazySingletonEx == null) {
                    return new LazySingletonEx();
                }
            }
            return lazySingletonEx;
        }
    }

    /**
     * 双重同步锁单例模式
     * 上面例子虽然加了同步锁，但它还是线程不安全的。虽然上面的例子不会出现多次初始
     * 化LazyDoubleCheckSingleton实例的情况，但是由于指令重排的原因，某些线程可
     * 能会获取到空对象，后续对该对象的操作将触发空指针异常。
     */
    static class LazyDoubleCheckSingleton {
        //要修复这个问题，只需要阻止指令重排即可，所以可以给instance属性加上volatile关键字
        private volatile static LazyDoubleCheckSingleton lazyDoubleCheckSingleton = null;

        private LazyDoubleCheckSingleton() {

        }

        public LazyDoubleCheckSingleton getInstance() {
            if (lazyDoubleCheckSingleton == null) {
                synchronized (this) {
                    if (lazyDoubleCheckSingleton == null) {
                        return new LazyDoubleCheckSingleton();
                    }
                }
            }
            return lazyDoubleCheckSingleton;
        }
    }

    /**
     * 静态内部类单例模式
     *JVM在类的初始化阶段会加Class对象初始化同步锁，同步多个线程对该类的初始化操作；
     * 静态内部类InnerClass的静态成员变量instance在方法区中只会有一个实例。
     * 在Java规范中，当以下这些情况首次发生时，A类将会立刻被初始化：
     * 1.A类型实例被创建；
     * 2.A类中声明的静态方法被调用；
     * 3.A类中的静态成员变量被赋值；
     * 4.A类中的静态成员被使用（非常量）；
     */
    static class StaticInnerClassSingleton {
        private StaticInnerClassSingleton() {

        }

        private static class InnerClass {
            private static StaticInnerClassSingleton instance = new StaticInnerClassSingleton();
        }

        public StaticInnerClassSingleton getInstance() {
            return InnerClass.instance;
        }
    }

    /**
     * 饿汉单例模式
     * “饿汉”意指在类加载的时候就初始化
     * 这种模式在类加载的时候就完成了初始化，所以并不存在线程安全性问题；但由于不是懒加载，
     * 饿汉模式不管需不需要用到实例都要去创建实例，如果创建了不使用，则会造成内存浪费。
     */
    static class HungrySingleton {
        private final static HungrySingleton hungrySingleton = new HungrySingleton();
        private HungrySingleton() {

        }
        public HungrySingleton getInstance() {
            return hungrySingleton;
        }
    }

    /**
     * 枚举单例模式是推荐的单例模式，它不仅可以防御序列化攻击，也可以防御反射攻击
     */
    enum EnumSingleton {
        INSTANCE;
        private Object Data;

        public Object getData() {
            return Data;
        }

        public void setData(Object data) {
            Data = data;
        }

        public static EnumSingleton getInstance() {
            return INSTANCE;
        }
    }

    /**
     * Java禁止通过反射创建枚举对象。
     * 正是因为枚举类型拥有这些天然的优势，所以用它创建单例是不错的选择，这也是Effective Java推荐的方式
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //验证下是否是单例
//        EnumSingleton instance = EnumSingleton.getInstance();
//        instance.setData(new Object());
//        EnumSingleton newInstance = EnumSingleton.getInstance();
//
//        System.out.println(instance);
//        System.out.println(newInstance);
//        System.out.println(instance.getData());
//        System.out.println(newInstance.getData());

        //序列化攻击
        EnumSingleton instance = EnumSingleton.getInstance();
        instance.setData(new Object());
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("file"));
        outputStream.writeObject(instance);
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("file"));
        EnumSingleton newInstance = (EnumSingleton) inputStream.readObject();
        System.out.println(instance);
        System.out.println(newInstance);
        System.out.println(instance == newInstance);
        System.out.println(instance.getData());
        System.out.println(newInstance.getData());
        System.out.println(instance.getData() == newInstance.getData());

        //反射攻击
        EnumSingleton instance2 = EnumSingleton.getInstance();
        Class<EnumSingleton> c = EnumSingleton.class;
        // 枚举类只包含一个(String,int)类型构造器
        Constructor<EnumSingleton> constructor = c.getDeclaredConstructor(String.class, int.class);
        constructor.setAccessible(true);
        EnumSingleton newInstance2 = constructor.newInstance("hello", 1);
        System.out.println(instance2 == newInstance2);

    }
}
