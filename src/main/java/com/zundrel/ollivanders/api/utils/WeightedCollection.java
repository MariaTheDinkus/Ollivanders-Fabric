package com.zundrel.ollivanders.api.utils;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class WeightedCollection<E> {

    private NavigableMap<Integer, E> map = new TreeMap<Integer, E>();
    private int total = 0;

    public WeightedCollection() {

    }

    public void add(int weight, E object) {
        if (weight <= 0) return;
        total += weight;
        map.put(total, object);
    }

    public E next(Random random) {
        int value = random.nextInt(total) + 1;
        return map.ceilingEntry(value).getValue();
    }

    public int getTotal() {
        return total;
    }
}
