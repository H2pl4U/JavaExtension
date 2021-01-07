package com.h2pl4u.javacsv;

import com.csvreader.CsvWriter;

import java.nio.charset.Charset;

/**
 * Created by Liuwei on 2020/9/15 15:41
 */
public class CsvWrite {
    public static void csvWrite() {
        String csvFilePath = "H://test.csv";
        try {
            CsvWriter csvWriter = new CsvWriter(csvFilePath, ',', Charset.forName("UTF-8"));
            //表头
            String[] csvHeaders = {"编号", "姓名", "年龄"};
            csvWriter.writeRecord(csvHeaders);
            //内容
            for (int i = 0; i < 5; i++) {
                String[] csvContent = {i + "", "username", "1" + i};
                csvWriter.writeRecord(csvContent);
            }
            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CsvWrite.csvWrite();
    }
}
