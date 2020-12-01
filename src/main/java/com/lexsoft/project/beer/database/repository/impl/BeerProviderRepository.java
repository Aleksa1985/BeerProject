package com.lexsoft.project.beer.database.repository.impl;

import com.lexsoft.project.beer.database.model.ProviderBeerDb;

import org.springframework.stereotype.Repository;

@Repository
public class BeerProviderRepository extends GenericDAO<ProviderBeerDb> {

    public ProviderBeerDb saveOrUpdate(ProviderBeerDb beerFromProvider) {
        if (beerFromProvider.getId() == null) {
            return create(beerFromProvider);
        } else {
            return update(beerFromProvider);
        }
    }

}
