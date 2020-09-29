package com.h2pl4u.designpattern;

import java.util.ArrayList;
import java.util.Date;

/**
 * 原型实例指定创建对象的种类，通过拷贝这些原型创建新的对象。
 * 适用于：
 * 类初始化消耗较多资源；
 * 循环体中生产大量对象的时候。
 * 优点：
 * 原型模式性能比直接new一个对象性能好；
 * 简化创建对象过程。
 * 缺点：
 * 对象必须重写Object克隆方法；
 * 复杂对象的克隆方法写起来较麻烦（深克隆、浅克隆）
 * Created by Liuwei on 2020/9/29 13:17
 */
public class Prototype {

    //浅拷贝 这种方式会比直接在循环中创建Student性能好
    static class Student implements Cloneable {
        private Integer age;
        private String name;

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    //深拷贝 当对象包含引用类型属性时，需要使用深克隆
    static class Teacher implements Cloneable {
        private Integer age;
        private String name;
        private Date birthDay;

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getBirthDay() {
            return birthDay;
        }

        public void setBirthDay(Date birthDay) {
            this.birthDay = birthDay;
        }

        @Override
        public String toString() {
            return "Teacher{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    ", birthDay=" + birthDay +
                    '}';
        }

        public Object clone() throws CloneNotSupportedException {
            Teacher teacher = (Teacher) super.clone();
            Date date = (Date) teacher.getBirthDay().clone();
            teacher.setBirthDay(date);
            return teacher;
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Student student = new Student();
        Teacher teacher = new Teacher();
        teacher.setBirthDay(new Date());
        ArrayList<Student> list1 = new ArrayList<>();
        ArrayList<Teacher> list2 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Student s = (Student) student.clone();
            s.setAge(20+i);
            s.setName("学生"+i);
            Teacher t = (Teacher) teacher.clone();
            t.setAge(30+i);
            t.setName("老师"+i);
            t.setBirthDay(new Date());
            list1.add(s);
            list2.add(t);
        }
        System.out.println(list1.toString());
        System.out.println(list2.toString());
    }
}
