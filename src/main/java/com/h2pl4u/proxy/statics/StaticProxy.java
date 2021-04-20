package com.h2pl4u.proxy.statics;

/**
 * Created on 2021/4/20 14:54
 *
 * @Author Liuwei
 */
public class StaticProxy implements MessageService {
    private MessageService messageService;

    public StaticProxy(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public String send(String message) {
        System.out.println("消息发送前置处理");
        String result = messageService.send(message);
        System.out.println("消息发送后置处理");
        return result;
    }
}
