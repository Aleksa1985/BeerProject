package com.lexsoft.project.beer.core.service;

import com.lexsoft.project.beer.database.model.BeerDb;

import java.util.List;

public interface BeerService {

    void saveBeers();

    BeerDb saveBeer(List<String> externalSystemIds);

    BeerDb findBeerById(Long id);

    List<BeerDb> findAllBeers();

    void deleteBeer(Long id);

    void deleteAllBeers(List<Long> ids);
}
