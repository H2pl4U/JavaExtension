package com.h2pl4u.designpattern;

/**
 * 备忘录
 * 在不违反封装的情况下获得对象的内部状态，从而在需要时可以将对象恢复到最初状态
 * Created by Liuwei on 2020/11/5 15:19
 */
public class Memento {
    interface Calculator {
        PreviousCalculationToCareTaker backupLastCalculation();

        void restorePreviousCalculation(PreviousCalculationToCareTaker memento);

        int getCalculationResult();

        void setFirstNumber(int firstNumber);

        void setSecondNumber(int secondNumber);
    }

    static class CalculatorImp implements Calculator {
        private int firstNumber;
        private int secondNumber;

        @Override
        public PreviousCalculationToCareTaker backupLastCalculation() {
            return new PreviousCalculationImp(firstNumber, secondNumber);
        }

        @Override
        public void restorePreviousCalculation(PreviousCalculationToCareTaker memento) {
            this.firstNumber = ((PreviousCalculationToOriginator) memento).getFirstNumber();
            this.secondNumber = ((PreviousCalculationToOriginator) memento).getSecondNumber();
        }

        @Override
        public int getCalculationResult() {
            return firstNumber + secondNumber;
        }

        @Override
        public void setFirstNumber(int firstNumber) {
            this.firstNumber = firstNumber;
        }

        @Override
        public void setSecondNumber(int secondNumber) {
            this.secondNumber = secondNumber;
        }
    }

    interface PreviousCalculationToOriginator {
        int getFirstNumber();

        int getSecondNumber();
    }

    interface PreviousCalculationToCareTaker {

    }

    static class PreviousCalculationImp implements PreviousCalculationToCareTaker, PreviousCalculationToOriginator {
        private int firstNumber;
        private int secondNumber;

        public PreviousCalculationImp(int firstNumber, int secondNumber) {
            this.firstNumber = firstNumber;
            this.secondNumber = secondNumber;
        }

        @Override
        public int getFirstNumber() {
            return firstNumber;
        }

        @Override
        public int getSecondNumber() {
            return secondNumber;
        }
    }

    public static void main(String[] args) {
        Calculator calculator = new CalculatorImp();

        calculator.setFirstNumber(10);
        calculator.setSecondNumber(100);
        System.out.println(calculator.getCalculationResult());

        PreviousCalculationToCareTaker memento = calculator.backupLastCalculation();

        calculator.setFirstNumber(17);
        calculator.setSecondNumber(-290);
        System.out.println(calculator.getCalculationResult());

        calculator.restorePreviousCalculation(memento);
        System.out.println(calculator.getCalculationResult());
    }
}
