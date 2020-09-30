package com.h2pl4u.designpattern;

/**
 * 代理模式
 * 为其他对象提供一种代理，以控制对这个对象的访问，代理对象在客户端和目标对象之间起到了中介的作用。
 * 适用于：
 * -保护目标对象；
 * -增强目标对象。
 * 优点：
 * -将代理对象和真实被调用的目标对象分离；
 * -降低耦合，拓展性好；
 * -保护目标对象，增强目标对象。
 * 缺点：
 * -造成类的数目增加，增加复杂度；
 * -客户端和目标对象增加代理对象，会造成处理速度变慢。
 * 静态代理
 * 通过在代码中显式地定义了一个代理类，在代理类中通过同名的方法对目标对象的方法进行包装，
 * 客户端通过调用代理类的方法来调用目标对象的方法。
 * Created by Liuwei on 2020/9/30 13:16
 */
public class StaticProxy {
    interface PieService {
        void makePie();
    }

    static class PieServiceImpl implements PieService {
        @Override
        public void makePie() {
            System.out.println("制作🥗派");
        }
    }

    public static class PieServiceProxy {
        private PieService pieService;

        public void makePie() {
            beforeMethod();
            pieService = new PieServiceImpl();
            pieService.makePie();
            afterMethod();
        }

        private void afterMethod() {
            System.out.println("保鲜");
        }

        private void beforeMethod() {
            System.out.println("准备材料");
        }
    }

    public static void main(String[] args) {
        PieServiceProxy proxy = new PieServiceProxy();
        proxy.makePie();
    }
}
