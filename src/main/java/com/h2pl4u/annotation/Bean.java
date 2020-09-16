package com.h2pl4u.annotation;

import java.util.Date;

/**
 * Created by Liuwei on 2020/9/15 16:00
 */
@Table(name = "Student")
public class Bean {
    @Column(name = "age", length = 3)
    int age;

    @Column(name = "userName", length = 10)
    String name;

    @Column(name = "birthday", defaultValue = "sysdate")
    Date birthday;
}
