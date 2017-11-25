package com.example.bloodsoul.normalflowingview.test;

import android.test.ApplicationTestCase;

import java.util.Random;

public class MyTest extends ApplicationTestCase {
    public MyTest(Class applicationClass) {
        super(applicationClass);
    }

    public static void main(String[] args) {
//        int num = BaseDrawer.getAnyRandInt(10);
//        System.out.println(num);
//        float num2 = BaseDrawer.getDownRandFloat(4, 10);
//        System.out.println(num2);

        System.out.println("" + Math.sqrt(4f));
    }

    /**
     * 获得0--n之内的不等概率随机整数，0概率最大，1次之，以此递减，n最小
     */
    public static int getAnyRandInt(int n) {
        int    max    = n + 1;
        int    bigend = ((1 + max) * max) / 2;
        Random rd     = new Random();
        int    x      = Math.abs(rd.nextInt() % bigend);
        int    sum    = 0;
        for (int i = 0; i < max; i++) {
            sum += (max - i);
            if (sum > x) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 获取[min, max)内的随机数，越大的数概率越小
     *
     * @param min
     * @param max
     * @return
     */
    public static float getDownRandFloat(float min, float max) {
        float bigend = ((min + max) * max) / 2f;
        // Random rd = new Random();
        // Math.abs(rd.nextInt() % bigend)
        float x = getRandom(min, bigend);
        int sum = 0;
        for (int i = 0; i < max; i++) {
            sum += (max - i);
            if (sum > x) {
                return i;
            }
        }
        return min;
    }

    /**
     * 获取在最大最小区间中的随机数
     * [min, max)
     *
     * @param min
     * @param max
     * @return
     */
    public static float getRandom(float min, float max) {
        if (max < min) {
            throw new IllegalArgumentException("max should bigger than min!!!!");
        }
        return (float) (min + Math.random() * (max - min));
    }
}
