package com.lexsoft.project.beer.config;

import lombok.extern.java.Log;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Log
@Component
public class EndpointsCOnfigurationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        log.info("***********************************************");
        log.info("***********************************************");
        log.info("Available endpoints : ");
        applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods()
                .forEach((e, v) -> {
                    log.info("Endpoint: ".concat(e.toString()));
                });
        log.info("***********************************************");
        log.info("***********************************************");
    }
}
