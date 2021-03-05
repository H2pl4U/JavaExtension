package com.h2pl4u.proxy.jdk;

import java.lang.reflect.Proxy;

/**
 * 获取代理对象的工厂类
 *
 * Created on 2021/3/5 13:23
 *
 * @Author Liuwei
 */
public class JdkProxyFactory {
    public static Object getProxy(Object target) {
        return Proxy.newProxyInstance(
                // 目标类的类加载
                target.getClass().getClassLoader(),
                // 代理需要实现的接口，可指定多个
                target.getClass().getInterfaces(),
                // 代理对象对应的自定义 InvocationHandler
                new DebugInvocationHandler(target)
        );
    }
}
