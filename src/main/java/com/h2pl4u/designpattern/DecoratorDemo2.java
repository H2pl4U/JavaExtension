package com.h2pl4u.designpattern;

/**
 * Created by Liuwei on 2020/10/20 17:08
 */
public class DecoratorDemo2 {
    public interface Shape {
        void draw();
    }

    public static class Rectangle implements Shape {
        @Override
        public void draw() {
            System.out.println("Shape: Rectangle");
        }
    }

    public static class Circle implements Shape {
        @Override
        public void draw() {
            System.out.println("Shape: Circle");
        }
    }

    public static abstract class ShapeDecorator implements Shape {
        protected Shape decoratorShape;

        public ShapeDecorator(Shape decoratorShape) {
            this.decoratorShape = decoratorShape;
        }

        public void draw() {
            decoratorShape.draw();
        }
    }

    public static class RedShapeDecorator extends ShapeDecorator {

        public RedShapeDecorator(Shape decoratorShape) {
            super(decoratorShape);
        }

        @Override
        public void draw() {
            decoratorShape.draw();
            setRedBorder(decoratorShape);
        }

        private void setRedBorder(Shape decoratedShape) {
            System.out.println("Border Color: Red");
        }
    }

    public static void main(String[] args) {
        Shape circle = new Circle();
        Shape redCircle = new RedShapeDecorator(new Circle());

        Shape redRectangle = new RedShapeDecorator(new Rectangle());
        System.out.println("Circle with normal border");
        circle.draw();

        System.out.println("\nCircle of red border");
        redCircle.draw();

        System.out.println("\nRectangle of red border");
        redRectangle.draw();
    }
}
