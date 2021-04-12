package com.h2pl4u.mxparser;

import org.mariuszgromada.math.mxparser.Expression;

/**
 * Created on 2021/1/5 10:13
 *
 * @Author Liuwei
 */
public class MXParserDemo02 {
    public static void main(String[] args) {
        long timeMillis2 = System.currentTimeMillis();
        double cal = Math.pow(1341243124124333214D, 2D) + Math.pow(12312313D, 5D);
        System.out.println(System.currentTimeMillis() - timeMillis2);
        System.out.println(cal);
        long timeMillis = System.currentTimeMillis();
        Expression e = new Expression("1341243124124333214^2 + 12312313^5");
        double calculate = e.calculate();
        System.out.println(System.currentTimeMillis() - timeMillis);
        System.out.println(calculate);
    }
}
