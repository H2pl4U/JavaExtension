package com.h2pl4u.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 方法拦截器
 * <p>
 * Created on 2021/3/5 14:38
 *
 * @Author Liuwei
 */
public class DebugMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before method:" + method.getName());
        Object invoke = methodProxy.invoke(o, objects);
        System.out.println("before method:" + method.getName());
        return invoke;
    }
}
