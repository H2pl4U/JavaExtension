package com.h2pl4u.proxy.cglib;

/**
 * Created on 2021/3/5 14:45
 *
 * @Author Liuwei
 */
public class MainApplication {
    public static void main(String[] args) {
        AliSmsService smsService = (AliSmsService) CglibProxyFactory.getProxy(AliSmsService.class);
        smsService.send("Hello cglib Proxy");
    }
}
