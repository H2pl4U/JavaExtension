package com.h2pl4u.proxy.statics;

/**
 * Created on 2021/4/20 14:55
 *
 * @Author Liuwei
 */
public class MessageServiceImpl implements MessageService {
    @Override
    public String send(String message) {
        System.out.println("send message: " + message);
        return message;
    }
}
