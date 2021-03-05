package com.h2pl4u.proxy.jdk;

/**
 * Created on 2021/3/5 13:19
 *
 * @Author Liuwei
 */
public class SmsServiceImpl implements SmsService {
    @Override
    public String send(String message) {
        System.out.println("send message: " + message);
        return message;
    }
}
