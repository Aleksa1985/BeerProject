package com.lexsoft.project.beer.database.repository.impl;

import com.lexsoft.project.beer.database.model.BeerDb;
import com.lexsoft.project.beer.database.model.ProviderBeerDb;
import com.lexsoft.project.beer.database.model.TemperatureDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class BeerRepository extends GenericDAO<BeerDb> {

    @Autowired
    TemperatureRepository temperatureRepository;
    @Autowired
    BeerProviderRepository beerProviderRepository;

    public BeerDb saveOrUpdate(BeerDb beerDb) {
        BeerDb resultBeer = null;
        if (beerDb.getId() == null) {
            resultBeer = create(beerDb);
            beerDb.setId(resultBeer.getId());
            saveorUpdateBeerDependencies(beerDb);
            resultBeer = beerDb;
        } else {
            update(beerDb);
            saveorUpdateBeerDependencies(beerDb);
            resultBeer = beerDb;
        }
        return resultBeer;
    }

    public List<BeerDb> findAllBeerDbs() {
        StringBuilder queryBuilder = new StringBuilder("SELECT b FROM BeerDb b");
        TypedQuery<BeerDb> query = em.createQuery(queryBuilder.toString(), BeerDb.class);
        List<BeerDb> BeerDbs = query.getResultList();
        return BeerDbs;
    }

    public Boolean deleteBeer(BeerDb beer) {
        beer.getTemperatureList().forEach(t -> temperatureRepository.delete(t));
        beerProviderRepository.delete(beer.getProviderBeerDb());
        delete(beer);
        return Boolean.TRUE;
    }

    public List<BeerDb> findBeerDbsByParams(Map<String, String> searchMap) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BeerDb> q = cb.createQuery(BeerDb.class);
        Root<BeerDb> root = q.from(BeerDb.class);
        List<Predicate> conditionsList = new ArrayList<>();

        List<Predicate> predicates = new ArrayList<>();
        searchMap.forEach((k, v) -> {
            Predicate predicate = cb.like((root.get(k)), "%".concat(v).concat("%"));
            predicates.add(predicate);
        });

        Predicate or = cb.or(predicates.toArray(new Predicate[0]));
        conditionsList.add(or);

        q.where(conditionsList.toArray(new Predicate[]{}));
        TypedQuery<BeerDb> query = em.createQuery(q);
        List<BeerDb> resultList = query.getResultList();
        return resultList;
    }

    private void saveorUpdateBeerDependencies(BeerDb beerFromDb) {
        //save and return temperatures
        List<TemperatureDb> resultTempList = new ArrayList<>();
        beerFromDb.getTemperatureList().forEach(tempDb -> {
            tempDb.setBeer(beerFromDb);
            TemperatureDb temperatureDb = temperatureRepository.saveOrUpdate(tempDb);
            resultTempList.add(temperatureDb);
        });
        beerFromDb.setTemperatureList(resultTempList);

        //save provider beer
        ProviderBeerDb providerBeer = beerFromDb.getProviderBeerDb();
        providerBeer.setBeer(beerFromDb);
        ProviderBeerDb providerBeerDb = beerProviderRepository.saveOrUpdate(providerBeer);
        beerFromDb.setProviderBeerDb(providerBeerDb);
    }


}
