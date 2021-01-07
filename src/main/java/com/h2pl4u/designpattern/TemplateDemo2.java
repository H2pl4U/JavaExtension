package com.h2pl4u.designpattern;

/**
 * Created by Liuwei on 2020/10/21 11:14
 */
public class TemplateDemo2 {
    public static abstract class Game {
        abstract void initialize();

        abstract void startPlay();

        abstract void endPlay();

        //模板方法
        public final void play() {
            //初始化游戏
            initialize();
            //开始游戏
            startPlay();
            //结束游戏
            endPlay();
        }
    }

    public static class Cricket extends Game {
        @Override
        void initialize() {
            System.out.println("Cricket Game Initialized! Start playing.");
        }

        @Override
        void startPlay() {
            System.out.println("Cricket Game start! Enjoy the game.");
        }

        @Override
        void endPlay() {
            System.out.println("Cricket Game finished! Thank u.");
        }
    }

    public static class Football extends Game {
        @Override
        void initialize() {
            System.out.println("Football Game Initialized! Start playing.");
        }

        @Override
        void startPlay() {
            System.out.println("Football Game start! Enjoy the game.");
        }

        @Override
        void endPlay() {
            System.out.println("Football Game finished! Thank u.");
        }
    }

    public static void main(String[] args) {
        Game game = new Cricket();
        game.play();
        System.out.println();
        game = new Football();
        game.play();
    }
}
