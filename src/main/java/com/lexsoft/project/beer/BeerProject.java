package com.lexsoft.project.beer;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class BeerProject {

     public static void main(String[] args){
         log.info("****************************************");
         log.info("****************************************");
         log.info("Beer project application starting point ");
         SpringApplication.run(BeerProject.class, args);
     }


}
