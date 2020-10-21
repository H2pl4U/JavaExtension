package com.h2pl4u.designpattern;

/**
 * 解释器模式
 * 解释器模式（Interpreter Pattern）提供了评估语言的语法或表达式的方式，它属于行为型模式。
 * 这种模式实现了一个表达式接口，该接口解释一个特定的上下文。这种模式被用在 SQL 解析、符号处理引擎等。
 * 介绍
 * 意图：给定一个语言，定义它的文法表示，并定义一个解释器，这个解释器使用该标识来解释语言中的句子。
 * 主要解决：对于一些固定文法构建一个解释句子的解释器。
 * 何时使用：如果一种特定类型的问题发生的频率足够高，那么可能就值得将该问题的各个实例表述为一
 * 个简单语言中的句子。这样就可以构建一个解释器，该解释器通过解释这些句子来解决该问题。
 * 如何解决：构件语法树，定义终结符与非终结符。
 * 关键代码：构件环境类，包含解释器之外的一些全局信息，一般是 HashMap。
 * 应用实例：编译器、运算表达式计算。
 * Created by Liuwei on 2020/10/21 14:28
 */
public class Interpreter {
    public interface Expression {
        boolean interpret(String context);
    }

    public static class TerminalExpression implements Expression {
        private String data;

        public TerminalExpression(String data) {
            this.data = data;
        }

        @Override
        public boolean interpret(String context) {
            if (context.contains(data)) {
                return true;
            }
            return false;
        }
    }

    public static class OrExpression implements Expression {
        private Expression expr1 = null;
        private Expression expr2 = null;

        public OrExpression(Expression expr1, Expression expr2) {
            this.expr1 = expr1;
            this.expr2 = expr2;
        }

        @Override
        public boolean interpret(String context) {
            return expr1.interpret(context) || expr2.interpret(context);
        }
    }

    public static class AndExpression implements Expression {
        private Expression expr1 = null;
        private Expression expr2 = null;

        public AndExpression(Expression expr1, Expression expr2) {
            this.expr1 = expr1;
            this.expr2 = expr2;
        }

        @Override
        public boolean interpret(String context) {
            return expr1.interpret(context) && expr2.interpret(context);
        }
    }

    public static Expression getMaleExpression() {
        //规则：Robert 和John是男性
        Expression robert = new TerminalExpression("Robert");
        Expression john = new TerminalExpression("John");
        return new OrExpression(robert, john);
    }

    public static Expression getMarriedWomanExpression() {
        //规则：Julie是一个已婚的女性
        Expression julie = new TerminalExpression("Julie");
        Expression married = new TerminalExpression("Married");
        return new OrExpression(julie, married);
    }

    public static void main(String[] args) {
        Expression isMale = getMaleExpression();
        Expression isMarriedWoman = getMarriedWomanExpression();
        System.out.println("John is male? " + isMale.interpret("John"));
        System.out.println("Julie is a married women? " + isMarriedWoman.interpret("Married Julie"));
    }
}
