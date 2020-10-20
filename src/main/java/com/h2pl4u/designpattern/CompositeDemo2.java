package com.h2pl4u.designpattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liuwei on 2020/10/20 16:47
 */
public class CompositeDemo2 {
    public static class Employee {
        private String name;
        private String dept;
        private int salary;
        private List<Employee> subordinates;

        public Employee(String name, String dept, int salary) {
            this.name = name;
            this.dept = dept;
            this.salary = salary;
            subordinates = new ArrayList<Employee>();
        }

        public void add(Employee e) {
            subordinates.add(e);
        }

        public void remove(Employee e) {
            subordinates.remove(e);
        }

        public List<Employee> getSubordinates() {
            return subordinates;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "name='" + name + '\'' +
                    ", dept='" + dept + '\'' +
                    ", salary=" + salary +
                    ", subordinates=" + subordinates +
                    '}';
        }
    }

    public static void main(String[] args) {
        Employee CEO = new Employee("Liuwei","CEO", 100000);
        Employee CTO = new Employee("fyh", "CTO", 60000);
        Employee CFO = new Employee("gf", "CFO", 50000);
        Employee clerk1 = new Employee("jjh","Marketing", 10000);
        Employee clerk2 = new Employee("zh","Marketing", 10000);
        Employee salesExecutive1 = new Employee("bjb","Sales", 10000);
        Employee salesExecutive2 = new Employee("hhl","Sales", 10000);
        CEO.add(CTO);
        CEO.add(CFO);
        CFO.add(clerk1);
        CFO.add(clerk2);
        CFO.add(salesExecutive1);
        CFO.add(salesExecutive2);
        System.out.println(CEO);
        for (Employee headEmployee : CEO.getSubordinates()) {
            System.out.println(headEmployee);
            for (Employee employee : headEmployee.getSubordinates()) {
                System.out.println(employee);
            }
        }
    }
}
