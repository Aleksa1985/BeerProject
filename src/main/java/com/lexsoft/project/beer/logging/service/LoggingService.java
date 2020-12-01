package com.lexsoft.project.beer.logging.service;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface LoggingService {

    void logRequest(HttpServletRequest httpServletRequest, Object body);

    void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body);

    void traceClientRequest(HttpRequest request, byte[] body) throws IOException;

    void traceClientResponse(ClientHttpResponse response, HttpRequest request) throws IOException;

}
