package com.lexsoft.project.beer.client;

import com.lexsoft.project.beer.exception.ExceptionEnum;
import com.lexsoft.project.beer.exception.ExceptionUtils;
import com.lexsoft.project.beer.exception.model.ErrorMessage;
import com.lexsoft.project.beer.exception.types.InternalWebException;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public abstract class AbstractExternalClient {

    protected <T, R> T call(String url, HttpMethod method, HttpHeaders headers, R body, RestTemplate restTemplate, Class<T> possibleModel) {
        T result;
        headers = Optional.ofNullable(headers).orElse(new HttpHeaders());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity<>(body, headers);

        ResponseEntity<T> exchange = restTemplate.exchange(URI.create(url), method, entity, possibleModel);
        if (exchange.getStatusCode().is2xxSuccessful()) {
            result = exchange.getBody();
        } else {
            List<ErrorMessage> errorMessages = ExceptionUtils.addError(ExceptionEnum.EXTERNAL_CLIENT_ERROR, null, exchange.getBody().toString());
            throw new InternalWebException(ExceptionEnum.EXTERNAL_CLIENT_ERROR.getStatusCode(), errorMessages);
        }
        return result;
    }

}
