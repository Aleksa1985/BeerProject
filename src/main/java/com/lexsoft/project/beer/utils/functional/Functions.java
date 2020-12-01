package com.lexsoft.project.beer.utils.functional;

@FunctionalInterface
public interface Functions<One,Two,Three,Result> {
    public Result apply(One one,Two two,Three three);
}
