package com.h2pl4u.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK 动态代理类
 *
 * Created on 2021/3/5 13:20
 *
 * @Author Liuwei
 */
public class DebugInvocationHandler implements InvocationHandler {

    /**
     * 代理类中真实对象
     */
    private final Object target;

    public DebugInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //调用方法之前，添加自定义的操作
        System.out.println("before method " + method.getName());
        Object invoke = method.invoke(target, args);
        //调用方法之后，添加自定义的操作
        System.out.println("after method " + method.getName());
        return invoke;
    }
}
