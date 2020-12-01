package com.lexsoft.project.beer.logging.interceptor;

import com.lexsoft.project.beer.logging.filter.RequestCorrelation;
import com.lexsoft.project.beer.logging.service.LoggingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.DispatcherType;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.UUID;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Autowired
    LoggingService loggingService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String currentCorrId = UUID.randomUUID().toString();
        RequestCorrelation.setId(currentCorrId);

        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name()) &&
                (request.getMethod().equals(HttpMethod.GET.name()) || request.getMethod().equals(HttpMethod.DELETE.name()))) {
            loggingService.logRequest(request, null);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request
            , HttpServletResponse response, Object o, ModelAndView modelAndView) {
        loggingService.logResponse(request, response, null);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) {

    }
}
