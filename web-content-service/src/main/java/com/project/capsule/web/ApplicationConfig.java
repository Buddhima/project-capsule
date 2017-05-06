package com.project.capsule.web;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.inject.Named;

/**
 * Created by buddhima on 4/24/17.
 */

@Configuration
public class ApplicationConfig {

    /*@Named
    static class JerseyConfig extends ResourceConfig {
        public JerseyConfig() {
            this.packages("com.project.capsule.rest");
        }
    }*/

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
}
