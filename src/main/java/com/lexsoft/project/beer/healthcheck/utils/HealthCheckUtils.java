package com.lexsoft.project.beer.healthcheck.utils;

import com.lexsoft.project.beer.database.repository.impl.HealthcheckRepository;
import com.lexsoft.project.beer.healthcheck.model.HealthCheckResponse;
import com.lexsoft.project.beer.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class HealthCheckUtils {

    public static HealthCheckResponse healthCheckResponse = HealthCheckResponse.builder()
            .database(HealthCheckResponse.DatasourceHealthcheck
                    .builder()
                    .build())
            .build();

    public static void populateHealtcheckBody(String applicationVersion ,HealthcheckRepository healthcheckMapper, Date currentTime) {
        String currentTimeLoc = DateUtils.SIMPLE_DATE_FORMAT.format(currentTime);
        databaseHC(healthcheckMapper,healthCheckResponse,currentTimeLoc);
        healthCheckResponse.setVersion(applicationVersion);
        healthCheckResponse.setStatus("UP");
    }

    private static HealthCheckResponse.DatasourceHealthcheck populateDatasourceHealthcheck(String status, String critical, String lasthealthyDate) {
        return HealthCheckResponse.DatasourceHealthcheck.builder()
                .critical(critical)
                .status(status)
                .lastHealthyCheck(lasthealthyDate)
                .build();
    }


    private static void databaseHC(HealthcheckRepository healthcheckRepository, HealthCheckResponse healthCheckResponse, String time){
        try {
            healthcheckRepository.checkConnection();
            healthCheckResponse.setDatabase(populateDatasourceHealthcheck("UP", "CRITICAL", time));;
        } catch (Exception ex) {
            ex.printStackTrace();
            healthCheckResponse.setDatabase(populateDatasourceHealthcheck("DOWN", "CRITICAL", time));
            log.error("There is a problem with postgress DB connection",ex);
        }
    }
}
