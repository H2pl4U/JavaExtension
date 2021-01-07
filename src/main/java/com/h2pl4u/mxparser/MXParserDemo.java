package com.h2pl4u.mxparser;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;
import org.mariuszgromada.math.mxparser.mXparser;

/**
 * Created on 2021/1/4 17:39
 *
 * @Author Liuwei
 */
public class MXParserDemo {
    public static void main(String[] args) {
        Argument days = new Argument("days = 30");
        Expression min = new Expression("min(days/10,1)", days);
        mXparser.consolePrintln(min.getExpressionString() + "----" + min.calculate());
        Expression e = new Expression("2-(32-4)/(23+4/5)-(2-4)*(4+6-98.2)+4");
        mXparser.consolePrintln("Res: " + e.getExpressionString() + " = " + e.calculate());
        Expression abs = new Expression("abs(-1)");
        mXparser.consolePrintln("Res: " + abs.getExpressionString() + " = " + abs.calculate());
        Expression sqrt = new Expression("sqrt(2)");
        mXparser.consolePrintln("Res: " + sqrt.getExpressionString() + " = " + sqrt.calculate());
        Function A = new Function("t(a,b) = 1/2 * a *b");
        Expression expression = new Expression("t(2,4)", A);
        mXparser.consolePrintln(expression.getExpressionString() + "-----------" + expression.calculate());
        Argument a = new Argument("a = 3");
        Argument b = new Argument("b = 3");
        Argument h = new Argument("h = 4");
        Expression expression1 = new Expression("t(b,h)", A, b, h, a);
        mXparser.consolePrintln(expression1.getExpressionString() + "-----------" + expression1.calculate());
        Expression expression2 = new Expression("1/2 * a *b", A, b, h, a);
        mXparser.consolePrintln(expression2.getExpressionString() + "-----------" + expression2.calculate());

        Argument x = new Argument("x = pi/2");
        Expression e20 = new Expression("sum(n,0,10,(-1)^n*(x^(2*n+1))/(2*n+1)!)", x);
        mXparser.consolePrintln(e20.getExpressionString() + "-----------" + e20.calculate());
    }
}
