package com.lexsoft.project.beer.repository;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.lexsoft.project.beer.database.model.BeerDb;
import com.lexsoft.project.beer.database.repository.impl.BeerProviderRepository;
import com.lexsoft.project.beer.database.repository.impl.BeerRepository;
import com.lexsoft.project.beer.database.repository.impl.TemperatureRepository;
import com.lexsoft.project.beer.utils.component.TestData;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update"
})
public class BeerReporistoryTest {

    static BeerDb beer1;
    static BeerDb beer2;
    static BeerDb beer3;
    static BeerDb beer4;

    @Autowired
    private BeerRepository beerRepository;
    @Autowired
    private TemperatureRepository temperatureRepository;
    @Autowired
    private BeerProviderRepository beerProviderRepository;

    @TestConfiguration
    static class BeerConversionTestConfiguration {
        @Bean
        BeerRepository beerRepository() {
            return new BeerRepository();
        }

        @Bean
        TemperatureRepository temperatureRepository() {
            return new TemperatureRepository();
        }

        @Bean
        BeerProviderRepository beerProviderRepository() {
            return new BeerProviderRepository();
        }
    }

    @BeforeAll
    public static void initiateTests() throws JsonProcessingException {
        List<BeerDb> beersFromDb = new TestData().getBeersFromDb();
        beer1 = beersFromDb.get(0);
        beer2 = beersFromDb.get(1);
        beer3 = beersFromDb.get(2);
        beer4 = beersFromDb.get(3);
    }

    @Test
    void saveBeerScenarioTest() {
        BeerDb beerToBeSaved = prepareSaveBeer(beer1);
        BeerDb beerDb = beerRepository.saveOrUpdate(beerToBeSaved);

        Assert.notNull(beerDb, "Object beer must not be null");
        Assert.notNull(beerDb.getId(), "Object must contain id");
        Assert.notNull(beerDb.getProviderBeerDb(), "Object providerBeer must not be null");
        Assert.notNull(beerDb.getProviderBeerDb().getId(), "Provider beer must contain id");
        Assert.notNull(beerDb.getTemperatureList(), "Temperature List must not be null");
        beerDb.getTemperatureList().forEach(temp -> {
            Assert.notNull(temp.getId(), "Temperature must contain id");
        });
    }

    @Test
    void findOneBeerScenarioTest() {
        BeerDb beerToBeSaved = prepareSaveBeer(beer1);
        BeerDb beerDb = beerRepository.saveOrUpdate(beerToBeSaved);

        BeerDb findBeerResult = beerRepository.find(beerDb.getId());

        Assert.notNull(findBeerResult, "Object beer must not be null");
        Assert.notNull(findBeerResult.getId(), "Object must contain id");
        Assert.notNull(findBeerResult.getProviderBeerDb(), "Object providerBeer must not be null");
        Assert.notNull(findBeerResult.getProviderBeerDb().getId(), "Provider beer must contain id");
        Assert.notNull(findBeerResult.getTemperatureList(), "Temperature List must not be null");
        findBeerResult.getTemperatureList().forEach(temp -> {
            Assert.notNull(temp.getId(), "Temperature must contain id");
        });
    }

    @Test
    void findAllBeers() {
        BeerDb beerToBeSaved = prepareSaveBeer(beer1);
        beerRepository.saveOrUpdate(beerToBeSaved);
        beerToBeSaved = prepareSaveBeer(beer2);
        beerRepository.saveOrUpdate(beerToBeSaved);
        beerToBeSaved = prepareSaveBeer(beer3);
        beerRepository.saveOrUpdate(beerToBeSaved);

        List<BeerDb> allBeerDbs = beerRepository.findAllBeerDbs();
        org.junit.Assert.assertEquals(3, allBeerDbs.size());

    }

    @Test
    void deleteOneBeerScenarioTest() {
        BeerDb beerToBeSaved = prepareSaveBeer(beer1);
        BeerDb beerDb = beerRepository.saveOrUpdate(beerToBeSaved);

        BeerDb findBeerResult = beerRepository.find(beerDb.getId());

        Assert.notNull(findBeerResult, "Object beer must not be null");
        Assert.notNull(findBeerResult.getId(), "Object must contain id");
        Assert.notNull(findBeerResult.getProviderBeerDb(), "Object providerBeer must not be null");
        Assert.notNull(findBeerResult.getProviderBeerDb().getId(), "Provider beer must contain id");
        Assert.notNull(findBeerResult.getTemperatureList(), "Temperature List must not be null");
        findBeerResult.getTemperatureList().forEach(temp -> {
            Assert.notNull(temp.getId(), "Temperature must contain id");
        });

        Boolean aBoolean = beerRepository.deleteBeer(findBeerResult);
        Assert.isTrue(aBoolean, "delete must return true");
        findBeerResult = beerRepository.find(beerDb.getId());
        Assert.isNull(findBeerResult, "Object must be null after deleting");

    }

    @Test
    void findAllBeersByParams() {
        BeerDb beerToBeSaved = prepareSaveBeer(beer1);
        beerRepository.saveOrUpdate(beerToBeSaved);
        beerToBeSaved = prepareSaveBeer(beer2);
        beerRepository.saveOrUpdate(beerToBeSaved);
        beerToBeSaved = prepareSaveBeer(beer4);
        beerRepository.saveOrUpdate(beerToBeSaved);

        List<BeerDb> allBeerDbs1 = beerRepository.findAllBeerDbs();

        Map<String, String> map = new HashMap<>();
        map.put("name", "searchTester");

        List<BeerDb> allBeerDbs = beerRepository.findBeerDbsByParams(map);
        org.junit.Assert.assertEquals(1, allBeerDbs.size());

    }

    private BeerDb prepareSaveBeer(BeerDb beer1) {
        BeerDb copy = beer1.toBuilder().build();
        copy.getTemperatureList().forEach(temp -> temp.setId(null));
        copy.setId(null);
        copy.getProviderBeerDb().setId(null);
        return copy;
    }


}
