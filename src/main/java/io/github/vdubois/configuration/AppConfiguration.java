package io.github.vdubois.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by vdubois on 08/11/16.
 */
@Configuration
public class AppConfiguration {

    @Bean
    AlwaysSampler alwaysSampler() {
        return new AlwaysSampler();
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
