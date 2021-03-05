package com.h2pl4u.proxy.cglib;

/**
 * Created on 2021/3/5 14:34
 *
 * @Author Liuwei
 */
public class AliSmsService {
    public String send(String message) {
        System.out.println("Ali Sms Send:" + message);
        return message;
    }
}
