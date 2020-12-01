package com.lexsoft.project.beer.logging;

import com.lexsoft.project.beer.logging.filter.RequestCorrelation;
import com.lexsoft.project.beer.logging.service.LoggingService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class WebClientInterceptor implements ClientHttpRequestInterceptor {

    @Autowired
    LoggingService loggingService;

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution) throws IOException {

        String correlationId = RequestCorrelation.getId() != null ? RequestCorrelation.getId() : UUID.randomUUID().toString();
        RequestCorrelation.setId(correlationId);
        request.getHeaders().add(RequestCorrelation.CORRELATION_ID, correlationId);
        loggingService.traceClientRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        response.getHeaders().add(RequestCorrelation.CORRELATION_ID, correlationId);
        loggingService.traceClientResponse(response, request);
        return response;
    }

}



