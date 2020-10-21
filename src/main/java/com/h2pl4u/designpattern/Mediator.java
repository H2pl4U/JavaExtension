package com.h2pl4u.designpattern;

import java.util.Date;

/**
 * Created by Liuwei on 2020/10/21 13:42
 */
public class Mediator {
    public static class ChatRoom {
        public static void showMessage(User user, String message) {
            System.out.println(new Date().toString() + "[" + user.getName() + "]:" + message);
        }
    }

    public static class User {
        private String name;

        public User(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void sendMessage(String message) {
            ChatRoom.showMessage(this, message);
        }
    }

    public static void main(String[] args) {
        User my = new User("马总");
        User qd = new User("强东");
        my.sendMessage("我对钱没兴趣");
        qd.sendMessage("不识妻美");
    }
}
