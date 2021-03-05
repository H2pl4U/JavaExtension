package com.h2pl4u.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * Created on 2021/3/5 14:40
 *
 * @Author Liuwei
 */
public class CglibProxyFactory {
    public static Object getProxy(Class<?> clazz) {
        // 创建动态代理增强类
        Enhancer enhancer = new Enhancer();
        // 设置类加载器
        enhancer.setClassLoader(clazz.getClassLoader());
        // 设置代理类
        enhancer.setSuperclass(clazz);
        // 设置方法拦截器
        enhancer.setCallback(new DebugMethodInterceptor());
        //返回代理类
        return enhancer.create();
    }
}
