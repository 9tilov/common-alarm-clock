package com.moggot.commonalarmclock;

import java.util.Random;

public class MathExample {

    private int num1, num2;

    public MathExample() {
        this.num1 = 0;
        this.num2 = 0;
    }

    public void createNumbers() {
        int min = 1, max = 19;
        Random rand = new Random();
        num1 = rand.nextInt((max - min) + 1) + min;
        while (num2 == num1)
            num2 = rand.nextInt((max - min) + 1) + min;
    }

    public boolean checkResult(int result) {
        return (result == (num1 + num2));
    }
}
