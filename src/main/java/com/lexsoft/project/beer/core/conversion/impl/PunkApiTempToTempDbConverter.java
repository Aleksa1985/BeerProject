package com.lexsoft.project.beer.core.conversion.impl;

import com.lexsoft.project.beer.client.provider.punkapi.model.TempData;
import com.lexsoft.project.beer.core.conversion.Converter;
import com.lexsoft.project.beer.database.model.TemperatureDb;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PunkApiTempToTempDbConverter implements Converter<TempData, TemperatureDb> {

    @Override
    public TemperatureDb convert(TempData temp) {
        return Optional.ofNullable(temp.getTemp())
                .map(td -> TemperatureDb.builder()
                        .unit(td.getUnit())
                        .value(td != null ? td.getValue() : 0)
                        .duration(temp.getDuration() != null ? temp.getDuration() : 0)
                        .build())
                .orElse(null);
    }

    @Override
    public List<TemperatureDb> convertList(List<TempData> tList) {
        List<TemperatureDb> resultList = new ArrayList<>();
        tList.forEach(t -> resultList.add(convert(t)));
        return resultList;
    }
}
