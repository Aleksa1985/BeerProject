package com.lexsoft.project.beer.utils.component;

import com.lexsoft.project.beer.web.dto.beer.BeerWebDto;
import com.lexsoft.project.beer.web.dto.information.InformationDto;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UATClientCalls extends ClientCallExecution {

    public ResponseEntity<BeerWebDto[]> findAllBeersCall(Integer port, TestRestTemplate template) {
        return executeCall("/v1/beers", port, HttpMethod.GET, template, BeerWebDto[].class);
    }

    public ResponseEntity<BeerWebDto> findSingleBeerCall(Integer port, TestRestTemplate template, String id) {
        return executeCall("/v1/beers/".concat(id), port, HttpMethod.GET, template, BeerWebDto.class);
    }

    public ResponseEntity<InformationDto> createBeersCall(Integer port, TestRestTemplate template) {
        return executeCall("/v1/beers", port, HttpMethod.POST, template, InformationDto.class);
    }

    public ResponseEntity<String> deleteOneBeer(Integer port, TestRestTemplate template, String id) {
        return executeCall("/v1/beers/".concat(id), port, HttpMethod.DELETE, template, String.class);
    }

    public ResponseEntity<String> deleteAllBeers(Integer port, TestRestTemplate template) {
        return executeCall("v1/beers", port, HttpMethod.DELETE, template, String.class);
    }

}
