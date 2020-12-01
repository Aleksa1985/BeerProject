package com.lexsoft.project.beer.conversion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lexsoft.project.beer.core.conversion.impl.BeerWebConverter;
import com.lexsoft.project.beer.database.model.BeerDb;
import com.lexsoft.project.beer.utils.MathUtils;
import com.lexsoft.project.beer.utils.component.TestData;
import com.lexsoft.project.beer.web.dto.beer.BeerWebDto;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
public class BeerWebConversionTest {

    private BeerDb beer1;
    private BeerDb beer2;
    private BeerDb beer3;

    @Autowired
    BeerWebConverter beerWebConverter;
    @Autowired
    TestData testData;

    @Before
    public void prepareTestData() throws JsonProcessingException {
        List<BeerDb> beersFromDb = testData.getBeersFromDb();
        beer1 = beersFromDb.get(0);
        beer2 = beersFromDb.get(1);
        beer3 = beersFromDb.get(2);
    }

    @TestConfiguration
    static class BeerConversionTestConfiguration {
        @Bean
        public BeerWebConverter beerWebConverter() {
            return new BeerWebConverter();
        }

        @Bean
        TestData testData() {
            return new TestData();
        }
    }

    @Test
    public void testoBeerDbToBeerWebTest() {

        BeerWebDto convert = beerWebConverter.convert(beer1);
        objectComparringAsserts(beer1, convert);

    }

    @Test
    public void testBeerDbToBeerWebMuyltipleTemperaturesTest() {

        BeerWebDto convert = beerWebConverter.convert(beer3);
        objectComparringAsserts(beer3, convert);

    }

    @Test
    public void testPunkApiToBeerDbBatchTest() {

        List<BeerDb> beerDbs = Arrays.asList(beer1, beer2);
        List<BeerWebDto> beerWebDtos = beerWebConverter.convertList(beerDbs);
        Assert.assertEquals(2, beerWebDtos.size());
        objectComparringAsserts(beer1, beerWebDtos.get(0));
        objectComparringAsserts(beer2, beerWebDtos.get(1));

    }

    private void objectComparringAsserts(BeerDb beerDb, BeerWebDto beerWebDto) {

        Assert.assertEquals(beerDb.getName(), beerWebDto.getName());
        Assert.assertEquals(beerDb.getDescription(), beerWebDto.getDescription());
        Assert.assertEquals(beerDb.getId(), beerWebDto.getId());

        beerDb.getTemperatureList().forEach(e -> {
            int i = beerDb.getTemperatureList().indexOf(e);
            Assert.assertEquals(e.getValue(), beerWebDto.getTemperature().getTemperatures().get(i).getValue());
            Assert.assertEquals(e.getDuration(), beerWebDto.getTemperature().getTemperatures().get(i).getDuration());
            Assert.assertEquals(e.getUnit(), beerWebDto.getTemperature().getTemperatures().get(i).getUnit());
            i++;
        });

        Assert.assertEquals(calculateAverage(beerDb), beerWebDto.getTemperature().getAverage());
    }

    private Double calculateAverage(BeerDb beerDb) {

        List<Integer> collect = beerDb.getTemperatureList().stream().map(tl -> tl.getValue()).collect(Collectors.toList());
        return MathUtils.calculateAverage(collect);

    }


}
