package com.h2pl4u.mxparser;

import org.mariuszgromada.math.mxparser.Function;

/**
 * Created on 2021/1/5 13:53
 *
 * @Author Liuwei
 */
public class MXParserDemo03 {
    public static void main(String[] args) {
        while (true) {
            long timeMillis = System.currentTimeMillis();
            Function func = new Function("f(x,y) = 1341243124124333214^x + 12312313^y");
            double calculate = func.calculate(2, 4);
            System.out.println(System.currentTimeMillis()-timeMillis);
        }
    }
}
