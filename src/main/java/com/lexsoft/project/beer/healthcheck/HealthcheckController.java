package com.lexsoft.project.beer.healthcheck;

import com.lexsoft.project.beer.healthcheck.model.HealthCheckResponse;
import com.lexsoft.project.beer.healthcheck.utils.HealthCheckUtils;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/health")
public class HealthcheckController {

    @GetMapping(value = "/healthcheck")
    public ResponseEntity getHealthcheck(){
        HealthCheckResponse response = HealthCheckUtils.healthCheckResponse;
        return ResponseEntity.ok(response);
    }

}
