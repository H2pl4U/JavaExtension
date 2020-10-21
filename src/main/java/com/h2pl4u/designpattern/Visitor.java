package com.h2pl4u.designpattern;

/**
 * Created by Liuwei on 2020/10/21 10:44
 */
public class Visitor {
    public interface ComputerPart {
        void accept(ComputerPartVisitor computerPartVisitor);
    }

    public static class Keyboard implements ComputerPart {
        @Override
        public void accept(ComputerPartVisitor computerPartVisitor) {
            computerPartVisitor.visit(this);
        }
    }

    public static class Monitor implements ComputerPart {
        @Override
        public void accept(ComputerPartVisitor computerPartVisitor) {
            computerPartVisitor.visit(this);
        }
    }

    public static class Mouse implements ComputerPart {
        @Override
        public void accept(ComputerPartVisitor computerPartVisitor) {
            computerPartVisitor.visit(this);
        }
    }

    public static class Computer implements ComputerPart {
        ComputerPart[] parts;

        public Computer() {
            parts = new ComputerPart[]{new Mouse(), new Keyboard(), new Monitor()};
        }

        @Override
        public void accept(ComputerPartVisitor computerPartVisitor) {
            for (int i = 0; i < parts.length; i++) {
                parts[i].accept(computerPartVisitor);
            }
            computerPartVisitor.visit(this);
        }
    }

    public interface ComputerPartVisitor {
        void visit(Computer computer);
        void visit(Mouse mouse);
        void visit(Monitor monitor);
        void visit(Keyboard keyboard);
    }

    public static class ComputerPartDisplayVisitor implements ComputerPartVisitor {
        @Override
        public void visit(Computer computer) {
            System.out.println("Displaying Computer.");
        }

        @Override
        public void visit(Mouse mouse) {
            System.out.println("Displaying Mouse.");
        }

        @Override
        public void visit(Monitor monitor) {
            System.out.println("Displaying Monitor.");
        }

        @Override
        public void visit(Keyboard keyboard) {
            System.out.println("Displaying Keyboard.");
        }
    }

    public static void main(String[] args) {
        ComputerPart computer = new Computer();
        computer.accept(new ComputerPartDisplayVisitor());
    }
}
