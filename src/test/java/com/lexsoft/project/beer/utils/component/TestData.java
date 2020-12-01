package com.lexsoft.project.beer.utils.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lexsoft.project.beer.client.provider.punkapi.model.Beer;
import com.lexsoft.project.beer.database.model.BeerDb;
import com.lexsoft.project.beer.utils.FileUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class TestData extends FileUtils {


    public List<Beer> getPunkApiBeers() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String fileAsString = getFileAsString("/json/providerBeers.json");
        Beer[] beers = mapper.readValue(fileAsString, Beer[].class);
        return Arrays.asList(beers);
    }

    public List<BeerDb> getBeersFromDb() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String fileAsString = getFileAsString("/json/dbBeers.json");
        BeerDb[] beers = mapper.readValue(fileAsString, BeerDb[].class);
        return Arrays.asList(beers);
    }


}
