package com.lexsoft.project.beer.core.conversion.impl;

import com.lexsoft.project.beer.core.conversion.Converter;
import com.lexsoft.project.beer.database.model.BeerDb;
import com.lexsoft.project.beer.database.model.TemperatureDb;
import com.lexsoft.project.beer.utils.MathUtils;
import com.lexsoft.project.beer.web.dto.beer.BeerWebDto;

import com.lexsoft.project.beer.web.dto.beer.GlobalBeerTemperatureDto;
import com.lexsoft.project.beer.web.dto.beer.TemperatureDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BeerWebConverter implements Converter<BeerDb,BeerWebDto> {

    @Override
    public BeerWebDto convert(BeerDb beer) {
        return BeerWebDto.builder()
                .description(beer.getDescription())
                .name(beer.getName())
                .temperature(getGlobalTemperatureDto(beer.getTemperatureList()))
                .id(beer.getId())
                .build();
    }

    @Override
    public List<BeerWebDto> convertList(List<BeerDb> beerDbList) {
        List<BeerWebDto> resultList = new ArrayList<>();
        beerDbList.forEach(t -> resultList.add(convert(t)));
        return resultList;
    }

    private GlobalBeerTemperatureDto getGlobalTemperatureDto(List<TemperatureDb> beerTemperatures){
        List<TemperatureDto> allBeerTemperatures = new ArrayList<>();
        beerTemperatures.forEach(temp -> {
            allBeerTemperatures.add(TemperatureDto.builder()
                    .duration(temp.getDuration())
                    .unit(temp.getUnit())
                    .value(temp.getValue())
                    .build());
        });
        return GlobalBeerTemperatureDto.builder()
                .temperatures(allBeerTemperatures)
                .average(MathUtils.calculateAverage(allBeerTemperatures.stream()
                        .map(e -> e.getValue())
                        .collect(Collectors.toList())))
                .build();
    }
}
