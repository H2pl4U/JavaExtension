package com.h2pl4u.javacsv;

import com.csvreader.CsvReader;

import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Liuwei on 2020/9/15 15:48
 */
public class CsvRead {
    public static void readCSV() {
        ArrayList<String[]> csvFileList = new ArrayList<String[]>();
        String csvFilePath = "H://test.csv";
        try {
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("UTF-8"));
            //跳过表头
            reader.readHeaders();
            //逐行读入除表头的数据
            while (reader.readRecord()) {
                System.out.println(reader.getRawRecord());
                csvFileList.add(reader.getValues());
            }
            reader.close();

            //遍历读取CSV每行每列
            for (int i = 0; i < csvFileList.size(); i++) {
                String[] cells = csvFileList.get(i);
                for (String str : cells) {
                    System.out.print(str + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CsvRead.readCSV();
    }
}
