package com.h2pl4u.proxy.statics;

import com.h2pl4u.proxy.jdk.JdkProxyFactory;
import com.h2pl4u.proxy.jdk.SmsService;
import com.h2pl4u.proxy.jdk.SmsServiceImpl;

/**
 * Created on 2021/3/5 13:27
 *
 * @Author Liuwei
 */
public class MainApplication {
    public static void main(String[] args) {
        MessageService messageService = new MessageServiceImpl();
        StaticProxy staticProxy = new StaticProxy(messageService);
        String str = staticProxy.send("hello static proxy");
        System.out.println(str);
    }
}
