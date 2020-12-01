package com.lexsoft.project.beer.client;

import com.lexsoft.project.beer.logging.WebClientInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfiguration {

    @Autowired
    WebClientInterceptor webClientInterceptor;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(
                new SimpleClientHttpRequestFactory()));

        List<ClientHttpRequestInterceptor> interceptors
                = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(webClientInterceptor);
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }


}
