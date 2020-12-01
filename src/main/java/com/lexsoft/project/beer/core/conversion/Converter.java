package com.lexsoft.project.beer.core.conversion;

import java.util.List;

public interface Converter<A, B> {

    B convert(A a);

    List<B> convertList(List<A> tList);

}
