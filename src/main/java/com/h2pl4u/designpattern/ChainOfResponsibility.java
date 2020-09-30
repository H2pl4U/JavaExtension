package com.h2pl4u.designpattern;

/**
 * 职责链模式
 * 职责链模式为请求创建一个接收此次请求对象的链。
 * 适用于：
 * 一个请求的处理需要多个对象当中的一个或几个协作处理；
 * 优点：
 * -请求的发送者和接受者（请求的处理）解耦；
 * -职责链可以动态的组合。
 * 缺点：
 * -职责链太长或者处理时间过长，影响性能；
 * -职责链可能过多
 * Created by Liuwei on 2020/9/30 15:31
 */
public class ChainOfResponsibility {
    static abstract class StringValidator {
        protected StringValidator validator;

        public void setNextValidator(StringValidator validator) {
            this.validator = validator;
        }

        public abstract void check(String value);
    }

    public static class StringLengthValidator extends StringValidator {
        @Override
        public void check(String value) {
            if (value != null && value.length() != 0) {
                System.out.println("字符串长度合法");
                if (validator != null) {
                    validator.check(value);
                }
            } else {
                System.out.println("字符串长度不合法");
            }
        }
    }

    public static class StringValueValidator extends StringValidator {
        @Override
        public void check(String value) {
            if (value.contains("fuck")) {
                System.out.println("字符串值不合法");
                if (validator != null) {
                    validator.check(value);
                }
            } else {
                System.out.println("字符串值合法");
            }
        }
    }

    /**
     * 套路和StringLengthValidator一样。接着创建一个客户端类，演示下如何让校验类形成一个链条
     * @param args
     */
    public static void main(String[] args) {
        StringLengthValidator stringLengthValidator = new StringLengthValidator();
        StringValueValidator stringValueValidator = new StringValueValidator();
        stringLengthValidator.setNextValidator(stringValueValidator);
        stringLengthValidator.check("hello");
    }

}
