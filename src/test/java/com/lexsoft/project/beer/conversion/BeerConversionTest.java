package com.lexsoft.project.beer.conversion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lexsoft.project.beer.client.provider.punkapi.model.Beer;
import com.lexsoft.project.beer.core.conversion.impl.PunkApiTempToTempDbConverter;
import com.lexsoft.project.beer.core.conversion.impl.PunkApiToBeerDbConverter;
import com.lexsoft.project.beer.database.model.BeerDb;
import com.lexsoft.project.beer.utils.component.TestData;

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

@RunWith(SpringRunner.class)
public class BeerConversionTest {

    private Beer beer1;
    private Beer beer2;
    private Beer beer3;

    @Autowired
    PunkApiToBeerDbConverter punkApiToBeerDbConverter;
    @Autowired
    PunkApiTempToTempDbConverter punkApiTempToTempDbConverter;
    @Autowired
    TestData testData;

    @TestConfiguration
    static class BeerConversionTestConfiguration {

        @Bean
        public PunkApiToBeerDbConverter punkApiToBeerDbConverter() {
            return new PunkApiToBeerDbConverter();
        }

        @Bean
        public PunkApiTempToTempDbConverter punkApiTempToTempDbConverter() {
            return new PunkApiTempToTempDbConverter();
        }

        @Bean
        public TestData testData() {
            return new TestData();
        }
    }

    @Before
    public void prepareTestData() throws JsonProcessingException {
        List<Beer> punkApiBeers = testData.getPunkApiBeers();
        beer1 = punkApiBeers.get(0);
        beer2 = punkApiBeers.get(1);
        beer3 = punkApiBeers.get(2);
    }

    @Test
    public void testPunkApiToBeerDbTest() {

        BeerDb convert = punkApiToBeerDbConverter.convert(beer1);
        Assert.assertEquals(beer1.getName(), convert.getName());
        Assert.assertEquals(beer1.getDescription(), convert.getDescription());
        Assert.assertEquals(beer1.getId(), convert.getProviderBeerDb().getSystemId());

        Assert.assertEquals(beer1.getMethod().getTempData().get(0).getTemp().getUnit(), convert.getTemperatureList().stream().findFirst().get().getUnit());
        Assert.assertEquals(beer1.getMethod().getTempData().get(0).getTemp().getValue(), convert.getTemperatureList().stream().findFirst().get().getValue());
        Assert.assertEquals(beer1.getMethod().getTempData().get(0).getDuration(), convert.getTemperatureList().stream().findFirst().get().getDuration());

    }

    @Test
    public void testPunkApiToBeerDbWithMultipleTemperaturesTest() {

        BeerDb convert = punkApiToBeerDbConverter.convert(beer3);
        Assert.assertEquals(beer3.getName(), convert.getName());
        Assert.assertEquals(beer3.getDescription(), convert.getDescription());
        Assert.assertEquals(beer3.getId(), convert.getProviderBeerDb().getSystemId());

        Assert.assertEquals(beer3.getMethod().getTempData().get(0).getTemp().getUnit(), convert.getTemperatureList().get(0).getUnit());
        Assert.assertEquals(beer3.getMethod().getTempData().get(0).getTemp().getValue(), convert.getTemperatureList().get(0).getValue());
        Assert.assertEquals(beer3.getMethod().getTempData().get(0).getDuration(), convert.getTemperatureList().get(0).getDuration());

        Assert.assertEquals(beer3.getMethod().getTempData().get(1).getTemp().getUnit(), convert.getTemperatureList().get(1).getUnit());
        Assert.assertEquals(beer3.getMethod().getTempData().get(1).getTemp().getValue(), convert.getTemperatureList().get(1).getValue());
        Assert.assertEquals(beer3.getMethod().getTempData().get(1).getDuration(), convert.getTemperatureList().get(1).getDuration());

    }

    @Test
    public void testPunkApiToBeerDbBatchTest() {

        List<Beer> beersList = Arrays.asList(beer1, beer2);
        List<BeerDb> beerDbs = punkApiToBeerDbConverter.convertList(beersList);
        Assert.assertEquals(2, beerDbs.size());
        BeerDb beerDb1 = beerDbs.get(0);
        Assert.assertEquals(beer1.getName(), beerDb1.getName());
        Assert.assertEquals(beer1.getDescription(), beerDb1.getDescription());
        Assert.assertEquals(beer1.getId(), beerDb1.getProviderBeerDb().getSystemId());

        Assert.assertEquals(beer1.getMethod().getTempData().get(0).getTemp().getUnit(), beerDb1.getTemperatureList().stream().findFirst().get().getUnit());
        Assert.assertEquals(beer1.getMethod().getTempData().get(0).getTemp().getValue(), beerDb1.getTemperatureList().stream().findFirst().get().getValue());
        Assert.assertEquals(beer1.getMethod().getTempData().get(0).getDuration(), beerDb1.getTemperatureList().stream().findFirst().get().getDuration());

        BeerDb beerDb2 = beerDbs.get(1);
        Assert.assertEquals(beer2.getName(), beerDb2.getName());
        Assert.assertEquals(beer2.getDescription(), beerDb2.getDescription());
        Assert.assertEquals(beer2.getId(), beerDb2.getProviderBeerDb().getSystemId());

        Assert.assertEquals(beer2.getMethod().getTempData().get(0).getTemp().getUnit(), beerDb2.getTemperatureList().stream().findFirst().get().getUnit());
        Assert.assertEquals(beer2.getMethod().getTempData().get(0).getTemp().getValue(), beerDb2.getTemperatureList().stream().findFirst().get().getValue());
        Assert.assertEquals(beer2.getMethod().getTempData().get(0).getDuration(), beerDb2.getTemperatureList().stream().findFirst().get().getDuration());

    }


}
