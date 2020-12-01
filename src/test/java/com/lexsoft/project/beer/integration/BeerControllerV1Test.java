package com.lexsoft.project.beer.integration;

import com.lexsoft.project.beer.utils.component.UATClientCalls;
import com.lexsoft.project.beer.web.dto.beer.BeerWebDto;
import com.lexsoft.project.beer.web.dto.information.InformationDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static com.lexsoft.project.beer.web.dto.MessageContainer.BEER_ASYNC_INFO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeerControllerV1Test {

    @Value("${beers.number}")
    Integer maxBeerNumber;

    @Autowired
    UATClientCalls uatClientCalls;

    @LocalServerPort
    int port;

    TestRestTemplate template = new TestRestTemplate();

    @Test
    public void getDataWhenDbIsEmpty() {
        ResponseEntity<BeerWebDto[]> responseEntity = uatClientCalls.findAllBeersCall(port, template);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(0, responseEntity.getBody().length);
    }

    @Test
    public void testUATScenario() throws InterruptedException {

        ResponseEntity<InformationDto> createReponse;
        ResponseEntity<String> deleteResponse;
        ResponseEntity<BeerWebDto[]> allBeersCall;
        ResponseEntity<BeerWebDto> singleBeerCall;
        ResponseEntity<String> deleteAllBeers;

        //get content from database
        allBeersCall = uatClientCalls.findAllBeersCall(port, template);
        Assert.assertEquals(HttpStatus.OK, allBeersCall.getStatusCode());
        Assert.assertEquals(0, allBeersCall.getBody().length);

        //create max number of beers in database
        createReponse = uatClientCalls.createBeersCall(port, template);
        Assert.assertEquals(HttpStatus.OK, createReponse.getStatusCode());
        Assert.assertEquals(BEER_ASYNC_INFO, createReponse.getBody().getMessage());

        //execution of repopulation is asyncronus we must wait for repoppulation
        Thread.sleep(4000);

        //get all max beers from DB
        allBeersCall = uatClientCalls.findAllBeersCall(port, template);
        Assert.assertEquals(HttpStatus.OK, allBeersCall.getStatusCode());
        Assert.assertEquals(maxBeerNumber.intValue(), allBeersCall.getBody().length);

        //delete two beers from the database
        BeerWebDto firstBeer = allBeersCall.getBody()[0];
        BeerWebDto secondBeer = allBeersCall.getBody()[1];

        deleteResponse = uatClientCalls.deleteOneBeer(port, template, String.valueOf(firstBeer.getId()));
        Assert.assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        deleteResponse = uatClientCalls.deleteOneBeer(port, template, String.valueOf(secondBeer.getId()));
        Assert.assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());

        //call find all beers again
        allBeersCall = uatClientCalls.findAllBeersCall(port, template);
        Assert.assertEquals(HttpStatus.OK, allBeersCall.getStatusCode());
        Assert.assertEquals(maxBeerNumber.intValue() - 2, allBeersCall.getBody().length);


        //create beers to maximum requested number in database
        createReponse = uatClientCalls.createBeersCall(port, template);
        Assert.assertEquals(HttpStatus.OK, createReponse.getStatusCode());
        Assert.assertEquals(BEER_ASYNC_INFO, createReponse.getBody().getMessage());

        Thread.sleep(1500);

        //get all max beers from DB
        allBeersCall = uatClientCalls.findAllBeersCall(port, template);
        Assert.assertEquals(HttpStatus.OK, allBeersCall.getStatusCode());
        Assert.assertEquals(maxBeerNumber.intValue(), allBeersCall.getBody().length);

        //get one beer by id
        singleBeerCall = uatClientCalls.findSingleBeerCall(port, template, String.valueOf(allBeersCall.getBody()[0].getId()));
        Assert.assertEquals(HttpStatus.OK, singleBeerCall.getStatusCode());


        //delete all beers from db
        deleteAllBeers = uatClientCalls.deleteAllBeers(port, template);
        Assert.assertEquals(HttpStatus.OK, allBeersCall.getStatusCode());

        //call find all beers again
        allBeersCall = uatClientCalls.findAllBeersCall(port, template);
        Assert.assertEquals(HttpStatus.OK, allBeersCall.getStatusCode());
        Assert.assertEquals(0, allBeersCall.getBody().length);


        System.out.println("UAT execution completed");
    }


}
