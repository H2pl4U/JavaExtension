package com.h2pl4u.designpattern;

import java.util.Observable;

/**
 * 观察者模式
 * 观察者模式定义了对象之间的一对多依赖，让多个观察者同时监听某个主题对象，
 * 当主体对象发生变化时，它的所有观察者都会收到响应的通知。
 * 优点：
 * -观察者和被观察者之间建立一个抽象的耦合；
 * -观察者模式支持广播通信。
 * 缺点：
 * -观察者之间有过多的细节依赖，提高时间消耗及程序复杂度；
 * -应避免循环调用。
 *
 * JDK对观察者模式提供了支持。
 * Created by Liuwei on 2020/9/30 15:14
 */
public class Observer {

    /**
     * 继承 Observable类, Blog是被观察的对象
     */
    static class Blog extends Observable {
        private String title;

        public Blog(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void comment(Comment comment) {
            System.out.println(comment.getNickname() + "评论了<" + this.title + "> ，" +
                    "评论内容：" + comment.getContent());
            // 设置标识位 changed = true，表示被观察者发生了改变
            setChanged();
            // 通知观察者，可以给观察者传递数据
            notifyObservers(comment);
        }
    }

    public static class Comment {
        private String nickname;
        private String content;

        public Comment(String nickname, String content) {
            this.nickname = nickname;
            this.content = content;
        }

        public String getNickname() {
            return nickname;
        }

        public String getContent() {
            return content;
        }
    }

    static class Author implements java.util.Observer {

        private String name;

        public Author(String name) {
            this.name = name;
        }

        /**
         * 观察者被通知后，就会调用这个方法
         *
         * @param o   被观察者对象
         * @param arg 被观察者传递过来的数据
         */
        @Override
        public void update(Observable o, Object arg) {
            Blog blog = (Blog) o;
            Comment comment = (Comment) arg;
            System.out.println("系统感知到" + this.name + "撰写的博文<" +
                    blog.getTitle() + ">收到了" + comment.getNickname() +
                    "的评论，评论内容为：" + comment.getContent());
        }
    }

    /**
     * 观察者对象需要实现JDK的Observer类，重写update方法。
     * 当被观察者对象调用了notifyObservers方法后，相应的观察者的update方法会被调用。
     * 值得注意的是，观察者的update方法里的逻辑最好进行异步化，这样在并发环境下可以提升程序性能。
     * @param args
     */
    public static void main(String[] args) {
        Blog blog = new Blog("Java从入门到放弃");
        Author author = new Author("H2pl4U");

        //添加观察者
        blog.addObserver(author);

        Comment comment = new Comment("撕葱",
                "感谢楼主的文章，让我及时放弃Java，回家继承了亿万家产");
        blog.comment(comment);
    }
}
