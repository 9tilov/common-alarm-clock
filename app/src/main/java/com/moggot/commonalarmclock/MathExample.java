package com.moggot.commonalarmclock;

import java.util.Random;

public class MathExample {

    private int num1, num2, result;

    public MathExample() {
        this.num1 = 0;
        this.num2 = 0;
        this.result = 0;
    }

    public void createExample() {
        int min = 1, max = 19;
        Random rand = new Random();
        num1 = rand.nextInt((max - min) + 1) + min;
        do
            num2 = rand.nextInt((max - min) + 1) + min;
        while (num2 == num1);
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getNum1() {
        return num1;
    }

    public int getNum2() {
        return num2;
    }

    public boolean isResultCorrect() {
        return (result == (num1 + num2));
    }
}
