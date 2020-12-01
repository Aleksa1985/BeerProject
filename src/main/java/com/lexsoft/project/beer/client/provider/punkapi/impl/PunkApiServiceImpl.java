package com.lexsoft.project.beer.client.provider.punkapi.impl;

import com.lexsoft.project.beer.client.AbstractExternalClient;
import com.lexsoft.project.beer.client.provider.punkapi.PunkApiClient;
import com.lexsoft.project.beer.client.provider.punkapi.model.Beer;
import com.lexsoft.project.beer.config.ExternalClientConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PunkApiServiceImpl extends AbstractExternalClient implements PunkApiClient {

    @Autowired
    ExternalClientConfiguration externalClientConfiguration;
    @Autowired
    RestTemplate restTemplate;

    @Override
    public Beer recieveBeer() {
        Beer[] beerResponse = call(externalClientConfiguration.getClient().getPunkapi().getUrl(),
                HttpMethod.GET, null, null, restTemplate, Beer[].class);
        return beerResponse[0];
    }
}
