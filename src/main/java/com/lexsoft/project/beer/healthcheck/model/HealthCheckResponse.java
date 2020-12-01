package com.lexsoft.project.beer.healthcheck.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HealthCheckResponse {

    String status;
    String version;
    DatasourceHealthcheck database;

    @Data
    @Builder
    public static class DatasourceHealthcheck {
        private String status;
        private String critical;
        private String lastHealthyCheck;
    }

}
