package com.h2pl4u.designpattern;

/**
 * 状态模式
 * 在状态模式（State Pattern）中，类的行为是基于它的状态改变的。这种类型的设计模式属于行为型模式。
 * 在状态模式中，我们创建表示各种状态的对象和一个行为随着状态对象改变而改变的 context 对象。
 * 介绍
 * 意图：允许对象在内部状态发生改变时改变它的行为，对象看起来好像修改了它的类。
 * 主要解决：对象的行为依赖于它的状态（属性），并且可以根据它的状态改变而改变它的相关行为。
 * 如何解决：将各种具体的状态类抽象出来。
 * 使用场景： 1、行为随状态改变而改变的场景。 2、条件、分支语句的代替者。
 * Created by Liuwei on 2020/10/21 13:34
 */
public class StatePattern {
    public interface State {
        void doAction(Context context);
    }

    public static class StartState implements State {
        @Override
        public void doAction(Context context) {
            System.out.println("Player is in start state");
            context.setState(this);
        }

        public String toString() {
            return "Start State";
        }
    }

    public static class StopState implements State {
        @Override
        public void doAction(Context context) {
            System.out.println("Player is in stop state");
            context.setState(this);
        }

        public String toString() {
            return "Stop State";
        }
    }

    public static class Context {
        private State state;

        public Context() {
            state = null;
        }

        public State getState() {
            return state;
        }

        public void setState(State state) {
            this.state = state;
        }
    }

    public static void main(String[] args) {
        Context context = new Context();
        StartState startState = new StartState();
        startState.doAction(context);
        System.out.println(context.getState().toString());
        StopState stopState = new StopState();
        stopState.doAction(context);
        System.out.println(context.getState().toString());
    }
}
