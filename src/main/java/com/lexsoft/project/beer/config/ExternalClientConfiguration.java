package com.lexsoft.project.beer.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "external")
public class ExternalClientConfiguration {

    private Client client;

    @Data
    public static class Client {

        private PunkApi punkapi;

        @Data
        public static class PunkApi {
            String url;
        }
    }

}
