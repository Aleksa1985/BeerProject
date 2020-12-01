package com.lexsoft.project.beer.utils;

import java.util.List;

public class MathUtils {

    public static double calculateAverage(List<Integer> elements) {
        Integer sum = 0;
        if(!elements.isEmpty()) {
            for (Integer el : elements) {
                sum += el;
            }
            return sum.doubleValue() / elements.size();
        }
        return sum;
    }
}
