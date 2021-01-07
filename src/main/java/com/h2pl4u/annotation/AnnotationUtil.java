package com.h2pl4u.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Liuwei on 2020/9/15 16:11
 */
public class AnnotationUtil {
    private static String getTableName(Class<?> bean) {
        String name = null;
        // 判断bean是否被@Table注解
        if (bean.isAnnotationPresent(Table.class)) {
            // 获取注解对象
            Annotation tableAnnotation = bean.getAnnotation(Table.class);
            try {
                // 获取@Table注解所对应的name
                Method method = Table.class.getMethod("name");
                name = (String) method.invoke(tableAnnotation);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return name;
    }

    private static List<ColumnBean> getColumns(Class<?> bean) {
        List<ColumnBean> columns = new ArrayList<ColumnBean>();
        Field[] fields = bean.getDeclaredFields();
        if (fields != null) {
            // 表里所有成员变量（fields）,获取其注解信息
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                // 判断是否被@Column注解标记
                if (field.isAnnotationPresent(Column.class)) {

                    String name = null;
                    int length = 0;
                    String defaultValue = null;
                    String type = null;

                    // 分别获取@Column注解里成员的值
                    Annotation columnAnnotation = field.getAnnotation(Column.class);
                    try {
                        Method nameMethod = Column.class.getMethod("name");
                        name = (String) nameMethod.invoke(columnAnnotation);

                        Method rangeMethod = Column.class.getMethod("length");
                        length = (Integer) rangeMethod.invoke(columnAnnotation);

                        Method defaultValueMethod = Column.class.getMethod("defaultValue");
                        defaultValue = (String) defaultValueMethod.invoke(columnAnnotation);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // 判断类型，Java类型转换为数据库类型
                    if (int.class.isAssignableFrom(field.getType())
                            || Integer.class.isAssignableFrom(field.getType())) {
                        type = "NUMBER";
                    } else if (String.class.isAssignableFrom(field.getType())) {
                        type = "VARCHAR2";
                    } else if (Date.class.isAssignableFrom(field.getType())) {
                        type = "DATE";
                    } else {
                        throw new RuntimeException("unspported type=" + field.getType().getSimpleName());
                    }
                    columns.add(new ColumnBean(type, name, length, defaultValue));
                }
            }
        }
        return columns;
    }

    /**
     * 用于描述Column
     */
    private static class ColumnBean {
        final String type;
        final String name;
        final int length;
        final String defaultValue;

        public ColumnBean(String type, String name, int length, String defaultValue) {
            this.type = type;
            this.name = name;
            this.length = length;
            this.defaultValue = defaultValue;
        }
    }

    /**
     * 生成SQL
     *
     * @param bean
     * @return
     */
    public static String createTable(Class<?> bean) {
        String tableName = getTableName(bean);
        List<ColumnBean> columns = getColumns(bean);
        if (tableName != null && !tableName.equals("") && !columns.isEmpty()) {
            StringBuilder createTableSql = new StringBuilder("create table ");
            // 拼接表名
            createTableSql.append(tableName);
            createTableSql.append("(");

            //拼接字段信息
            for (int i = 0; i < columns.size(); i++) {
                ColumnBean column = columns.get(i);
                createTableSql.append(column.name);
                createTableSql.append(" ");
                createTableSql.append(column.type);
                int length = column.length;
                if (length != 0) {
                    createTableSql.append("(");
                    createTableSql.append(column.length);
                    createTableSql.append(")");
                }
                String defaultValue = column.defaultValue;
                if (defaultValue != null && defaultValue.length() != 0) {
                    createTableSql.append(" default ");
                    createTableSql.append(defaultValue);
                }
                if (i != columns.size() - 1) {
                    createTableSql.append(",");
                }
            }
            createTableSql.append(")");
            return createTableSql.toString();
        } else {
            throw new RuntimeException("table's name is null");
        }
    }
}
