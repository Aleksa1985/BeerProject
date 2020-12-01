package com.lexsoft.project.beer.utils.functional;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FunctionImpl {

    static Function<List<String>,String> concatAll = list -> list.stream().collect(Collectors.joining(" "));
    static Functions<Integer, Integer, Integer,Double> average = (a,b,c) -> Double.valueOf(a+b+c)/3;
    static Function<String,Integer> calculateWords  = list -> list.length();


    public static void main(String[] args) {
        Integer apply = concatAll.andThen(calculateWords).apply(Arrays.asList("Aleksa", "Petar", "Dejan"));
    }

}
