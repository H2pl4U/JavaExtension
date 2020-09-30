package com.h2pl4u.designpattern;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合模式
 * 将对象组合成树形结构以表示“部分-整体”的层次结构，使客户端对单个对象和组合对象保持一致的方式处理。
 * 适用于：
 * -客户端可以忽略组合对象与单个对象的差异；
 * -处理树形结构数据。
 * 优点:
 * -层次清晰；
 * -客户端不必关系层次差异，方便控制；
 * -符合开闭原则。
 * 缺点：
 * 树形处理较为复杂。
 * Created by Liuwei on 2020/9/30 10:30
 */
public class Composite {
    static class AbstractMenuButton {
        public void add(AbstractMenuButton abstractMenuButton) {
            throw new UnsupportedOperationException("不支持创建操作");
        }

        public String getName() {
            throw new UnsupportedOperationException("不支持创建操作");
        }

        public String getType() {
            throw new UnsupportedOperationException("不支持类型获取");
        }

        public String getIcon() {
            throw new UnsupportedOperationException("不支持图标");
        }

        public void print() {
            throw new UnsupportedOperationException("不支持打印操作");
        }
    }

    static class Button extends AbstractMenuButton {
        private String name;

        public Button(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String getType() {
            return "按钮";
        }

        @Override
        public void print() {
            System.out.println(getName() + "【" + getType() + "】");
        }
    }

    public static class Menu extends AbstractMenuButton {
        private List<AbstractMenuButton> items = new ArrayList<>();
        private String name;
        private String icon;
        private Integer level;

        public Menu(String name, String icon, Integer level) {
            this.name = name;
            this.icon = icon;
            this.level = level;
        }

        @Override
        public void add(AbstractMenuButton abstractMenuButton) {
            items.add(abstractMenuButton);
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String getType() {
            return "菜单";
        }

        @Override
        public String getIcon() {
            return this.icon;
        }

        @Override
        public void print() {
            System.out.println(getIcon() + getName() + "【" + getType() + "】");
            for (AbstractMenuButton item : items) {
                if (this.level != null) {
                    for (int i = 0; i < this.level; i++) {
                        System.out.print("    ");
                    }
                }
                item.print();
            }
        }
    }

    public static void main(String[] args) {
        Menu userMenu = new Menu("用户管理","🧑",2);
        Button creteUser = new Button("新增用户");
        Button updateUser = new Button("修改用户");
        Button deleteUser = new Button("删除用户");
        userMenu.add(creteUser);
        userMenu.add(updateUser);
        userMenu.add(deleteUser);

        Menu logMenu = new Menu("操作日志","📃",2);
        Button export = new Button("导出Excel");
        logMenu.add(export);

        Menu systemMenu = new Menu("系统管理","🖥",1);
        systemMenu.add(userMenu);
        systemMenu.add(logMenu);
        systemMenu.print();
    }
}
