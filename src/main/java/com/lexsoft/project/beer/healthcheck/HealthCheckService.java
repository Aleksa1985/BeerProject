package com.lexsoft.project.beer.healthcheck;

import com.lexsoft.project.beer.database.repository.impl.HealthcheckRepository;

import com.lexsoft.project.beer.healthcheck.utils.HealthCheckUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HealthCheckService {

    @Value("${project.application.version}")
    String applicationVersion;
    @Autowired
    HealthcheckRepository healthcheckRepository;

    @Scheduled(fixedDelay = 5000)
    public void initiateHealthCheck() {
        HealthCheckUtils.populateHealtcheckBody(applicationVersion, healthcheckRepository, new Date());
    }


}
