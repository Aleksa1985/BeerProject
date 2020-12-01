package com.lexsoft.project.beer.core.conversion.impl;

import com.lexsoft.project.beer.client.provider.punkapi.model.Beer;
import com.lexsoft.project.beer.core.conversion.Converter;
import com.lexsoft.project.beer.database.model.BeerDb;
import com.lexsoft.project.beer.database.model.ProviderBeerDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PunkApiToBeerDbConverter implements Converter<Beer, BeerDb> {

    @Autowired
    PunkApiTempToTempDbConverter tempToTempDbConverter;

    @Override
    public BeerDb convert(Beer beer) {
        return BeerDb.builder()
                .name(beer.getName())
                .description(beer.getDescription())
                .temperatureList(Optional.ofNullable(beer.getMethod())
                        .map(meth -> meth.getTempData())
                        .map(tempData -> tempToTempDbConverter.convertList(tempData))
                        .orElse(new ArrayList<>()))
                .providerBeerDb(ProviderBeerDb.builder()
                        .systemId(beer.getId())
                        .build())
                .build();
    }

    @Override
    public List<BeerDb> convertList(List<Beer> beerList) {
        List<BeerDb> resultList = new ArrayList<>();
        beerList.forEach(t -> resultList.add(convert(t)));
        return resultList;
    }


}
