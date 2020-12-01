package com.lexsoft.project.beer.utils.interfaces;

public class TestInterfaceImpl implements TestInterface {

    public static void main(String[] args) {
        new TestInterfaceImpl().print(3, 4);

    }

    public void print(Integer a, Integer b) {
        var result = defaultMe(a, b);
        System.out.println(result);
    }


}
