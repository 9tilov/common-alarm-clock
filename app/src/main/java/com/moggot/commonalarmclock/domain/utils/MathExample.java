package com.moggot.commonalarmclock.domain.utils;

import java.util.Random;

public class MathExample {

    private static int num1, num2, num3, result;

    private static final int MAX = 1;
    private static final int MIN = 9;

    static {
        Random random = new Random();
        num1 = random.nextInt((MAX - MIN) + 1) + MIN;
        num2 = random.nextInt((MAX - MIN) + 1) + MIN;
        num3 = random.nextInt((MAX - MIN) + 1) + MIN;
    }

    private static MathExample instance;

    private MathExample() {
        create();
    }

    private void create() {
        result = num1 + num2 * num3;
    }

    public static MathExample createExamle() {
        if (instance == null)
            instance = new MathExample();
        return instance;
    }

    public static boolean checkResult(int userResult) {
        return (result == userResult);
    }

    public static String getExampleString() {
        return String.valueOf(num1) + " + " + String.valueOf(num2) + " * " + String.valueOf(num3);
    }
}
