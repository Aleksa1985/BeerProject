package com.lexsoft.project.beer.utils.component;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class ClientCallExecution {

    private String createUrl(String path, Integer port) {
        return "http://localhost:".concat(String.valueOf(port)).concat(path);
    }

    protected <T> ResponseEntity<T> executeCall(String uriPath, Integer port, HttpMethod method, TestRestTemplate template, Class<T> clazz) {
        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<T> executionResponse =
                template.exchange(createUrl(uriPath, port), method, entity, clazz);
        return executionResponse;
    }

}
