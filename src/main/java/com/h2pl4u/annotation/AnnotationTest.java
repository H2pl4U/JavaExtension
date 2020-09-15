package com.h2pl4u.annotation;

/**
 * 通过自定义注解对实体类Bean创建数据库表的sql语句
 * Created by Liuwei on 2020/9/15 16:13
 */
public class AnnotationTest {
    public static void main(String[] args) {
        System.out.println(AnnotationUtil.createTable(Bean.class));
    }
}
