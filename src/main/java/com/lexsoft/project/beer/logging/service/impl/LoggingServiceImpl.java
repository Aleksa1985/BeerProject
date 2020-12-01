package com.lexsoft.project.beer.logging.service.impl;

import com.lexsoft.project.beer.logging.service.LoggingService;

import lombok.extern.java.Log;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Component
@Log
public class LoggingServiceImpl implements LoggingService {

    @Override
    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> parameters = buildParametersMap(httpServletRequest);

        stringBuilder.append("REQUEST ");
        stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
        stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
        stringBuilder.append("headers=[").append(buildHeadersMap(httpServletRequest)).append("] ");

        if (!parameters.isEmpty()) {
            stringBuilder.append("parameters=[").append(parameters).append("] ");
        }

        if (body != null) {
            stringBuilder.append("body=[" + body + "]");
        }

        log.info(stringBuilder.toString());
    }

    @Override
    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("RESPONSE ");
        stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
        stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
        stringBuilder.append("responseHeaders=[").append(buildHeadersMap(httpServletResponse)).append("] ");
        stringBuilder.append("responseBody=[").append(body).append("] ");

        log.info(stringBuilder.toString());
    }

    @Override
    public void traceClientRequest(HttpRequest request, byte[] body) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("REQUEST ");
        stringBuilder.append("method=[").append(request.getMethod()).append("] ");
        stringBuilder.append("path=[").append(request.getURI().getRawPath()).append("] ");
        stringBuilder.append("headers=[").append(buildHeadersMapFromHttpRequest(request)).append("] ");
        stringBuilder.append("body=[").append(new String(body)).append("] ");
        log.info(stringBuilder.toString());
    }

    @Override
    public void traceClientResponse(ClientHttpResponse response, HttpRequest request) throws IOException {
        StringBuilder bodyStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
        String line = bufferedReader.readLine();
        while (line != null) {
            bodyStringBuilder.append(line);
            bodyStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("REQUEST ");
        stringBuilder.append("status=[").append(response.getStatusCode()).append("] ");
        stringBuilder.append("path=[").append(request.getURI().getRawPath()).append("] ");
        stringBuilder.append("headers=[").append(response.getHeaders()).append("] ");
        stringBuilder.append("body=[").append(bodyStringBuilder.toString()).append("]");
        log.info(stringBuilder.toString());

    }

    private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }

    private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    private Map<String, String> buildHeadersMap(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();

        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            map.put(header, response.getHeader(header));
        }

        return map;
    }

    private Map<String, String> buildHeadersMapFromHttpRequest(HttpRequest httpRequest) {
        Map<String, String> resultMap = new HashMap<>();
        httpRequest.getHeaders().forEach((k,v) -> {
            resultMap.put(k,String.join(",",v));
        });
        return resultMap;
    }





}
