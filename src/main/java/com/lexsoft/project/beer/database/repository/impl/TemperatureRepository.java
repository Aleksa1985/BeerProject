package com.lexsoft.project.beer.database.repository.impl;

import com.lexsoft.project.beer.database.model.TemperatureDb;

import org.springframework.stereotype.Repository;

@Repository
public class TemperatureRepository extends GenericDAO<TemperatureDb> {

    public TemperatureDb saveOrUpdate(TemperatureDb temperature) {
        if (temperature.getId() == null) {
            return create(temperature);
        } else {
            return update(temperature);
        }
    }

}
