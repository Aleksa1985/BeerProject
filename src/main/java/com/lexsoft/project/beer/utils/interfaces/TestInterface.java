package com.lexsoft.project.beer.utils.interfaces;

public interface TestInterface {

    default Integer defaultMe(Integer a,Integer b){
        return a+b;
    }
}
