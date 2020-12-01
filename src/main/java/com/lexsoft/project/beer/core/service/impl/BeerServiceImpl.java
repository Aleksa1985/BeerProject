package com.lexsoft.project.beer.core.service.impl;

import com.lexsoft.project.beer.client.provider.punkapi.PunkApiClient;
import com.lexsoft.project.beer.client.provider.punkapi.model.Beer;
import com.lexsoft.project.beer.config.BeersGlobalConfiguration;
import com.lexsoft.project.beer.core.conversion.Converter;
import com.lexsoft.project.beer.core.service.BeerService;
import com.lexsoft.project.beer.database.model.BeerDb;
import com.lexsoft.project.beer.database.repository.impl.BeerRepository;
import com.lexsoft.project.beer.exception.ExceptionEnum;
import com.lexsoft.project.beer.exception.ExceptionUtils;
import com.lexsoft.project.beer.exception.types.InternalWebException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BeerServiceImpl implements BeerService {

    @Autowired
    BeerRepository beerRepository;
    @Autowired
    PunkApiClient punkApiClient;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    BeersGlobalConfiguration beersGlobalConfiguration;
    @Autowired
    Converter<Beer, BeerDb> converter;

    @Override
    @Async
    @Transactional
    public void saveBeers() {

        List<BeerDb> allBeerDbs = beerRepository.findAllBeerDbs();
        List<String> externalSystemIds = allBeerDbs.stream()
                .map(b -> b.getProviderBeerDb().getSystemId()).
                        collect(Collectors.toList());

        Integer numberOfBeersToFIll = Optional.ofNullable(allBeerDbs)
                .filter(abdb -> !abdb.isEmpty())
                .filter(abdb -> abdb.size() < beersGlobalConfiguration.getNumber())
                .map(abdb -> beersGlobalConfiguration.getNumber() - abdb.size())
                .orElse(beersGlobalConfiguration.getNumber());

        while (numberOfBeersToFIll != 0) {
            saveBeer(externalSystemIds);
            numberOfBeersToFIll--;
        }
    }

    @Override
    public BeerDb findBeerById(Long id) {
        return beerRepository.find(id);
    }

    @Override
    public List<BeerDb> findAllBeers() {
        List<BeerDb> allBeersFromDB = beerRepository.findAllBeerDbs();
        return allBeersFromDB;
    }

    @Override
    @Transactional
    public void deleteBeer(Long id) {
        Optional.ofNullable(findBeerById(id))
                .map(e -> beerRepository.deleteBeer(e))
                .orElseThrow(
                        () -> new InternalWebException(ExceptionEnum.OBJECT_DOES_NOT_EXIST.getStatusCode(),
                                ExceptionUtils.addError(ExceptionEnum.OBJECT_DOES_NOT_EXIST, null, "beer",
                                        String.valueOf(id)))
                );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteAllBeers(List<Long> ids) {
        List<BeerDb> allBeerDbs = beerRepository.findAllBeerDbs();
        allBeerDbs.stream().filter(e -> ids != null ? ids.contains(e.getId()) : Boolean.TRUE)
                .collect(Collectors.toList());

        Optional.of(beerRepository.findAllBeerDbs())
                .filter(beers -> !beers.isEmpty())
                .ifPresent(beers -> {
                    beers.forEach(b -> deleteBeer(b.getId()));
                });
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BeerDb saveBeer(List<String> storedExternalSystemIds) {
        Beer beer = punkApiClient.recieveBeer();
        return Optional.ofNullable(storedExternalSystemIds)
                .filter(esi -> !storedExternalSystemIds.contains(beer.getId()))
                .map(esi -> {
                    BeerDb beerDb = converter.convert(beer);
                    return beerRepository.saveOrUpdate(beerDb);
                })
                .orElse(null);
    }
}

