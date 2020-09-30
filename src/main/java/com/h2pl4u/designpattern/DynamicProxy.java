package com.h2pl4u.designpattern;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 * JDK的动态代理只能代理接口，通过接口的方法名在动态生成的代理类中调用业务实现类的同名方法。
 * 静态代理的缺点就是每需要代理一个类，就需要手写对应的代理类。这个问题可以用动态代理来解决
 *
 * CGLib代理
 * 通过继承来实现，生成的代理类就是目标对象类的子类，通过重写业务方法来实现代理。
 * Spring对代理模式的拓展
 * -当Bean有实现接口时，使用JDK动态代理；
 * -当Bean没有实现接口时，使用CGLib代理。
 * Created by Liuwei on 2020/9/30 13:30
 */
public class DynamicProxy {
    interface IceCreamService {
        void makeIceCream(String fruit);
    }

    static class IceCreamServiceImpl implements IceCreamService {
        public void makeIceCream(String fruit) {
            System.out.println("制作" + fruit + "🍦");
        }
    }

    public static class IceCreamDynamicProxy implements InvocationHandler {
        //代理的目标对象
        private Object object;

        public IceCreamDynamicProxy(Object object) {
            this.object = object;
        }

        public Object proxy() {
            Class<?> clazz = object.getClass();
            //生成代理对象
            return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
        }

        /**
         * @param proxy  动态生成的代理对象
         * @param method 代理方法
         * @param args   代理方法的方法参数
         * @return 结果
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            beforeMethod(object);
            //反射执行代理对象的目标方法
            Object result = method.invoke(object, args);
            afterMethod(object);
            return result;
        }

        private void beforeMethod(Object object) {
            if (object instanceof StaticProxy.PieService) {
                System.out.println("准备派的材料");
            } else if (object instanceof IceCreamService) {
                System.out.println("准备冰淇淋的材料");
            } else {
                throw new RuntimeException("暂时不支持代理" + object.getClass() + "类型");
            }
        }

        private void afterMethod(Object object) {
            if (object instanceof StaticProxy.PieService) {
                System.out.println("保鲜派");
            } else if (object instanceof IceCreamService) {
                System.out.println("保鲜冰淇淋");
            } else {
                throw new RuntimeException("暂时不支持代理" + object.getClass() + "类型");
            }
        }
    }

    public static void main(String[] args) {
        StaticProxy.PieService pieServiceDynamicProxy = (StaticProxy.PieService) new IceCreamDynamicProxy(new StaticProxy.PieServiceImpl()).proxy();
        pieServiceDynamicProxy.makePie();
        System.out.println("------------------------");
        IceCreamService iceCreamServiceDynamicProxy = (IceCreamService) new IceCreamDynamicProxy(new IceCreamServiceImpl()).proxy();
        iceCreamServiceDynamicProxy.makeIceCream("🍓");
    }
}
