package com.zundrel.ollivanders.common.utils;

import java.util.Random;

public class RandomUtils {
    public static int getRangedRandom(int min, int max)
    {
        return min + new Random().nextInt((max - min) + 1);
    }

    public static double getRangedRandom(float min, float max)
    {
        return min + Math.random() * (max - min);
    }
}
