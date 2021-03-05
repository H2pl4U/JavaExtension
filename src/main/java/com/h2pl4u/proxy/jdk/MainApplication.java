package com.h2pl4u.proxy.jdk;

/**
 * Created on 2021/3/5 13:27
 *
 * @Author Liuwei
 */
public class MainApplication {
    public static void main(String[] args) {
        SmsService smsService = (SmsService) JdkProxyFactory.getProxy(new SmsServiceImpl());
        smsService.send("hello jdk proxy");
    }
}
