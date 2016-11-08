package io.github.vdubois.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by vdubois on 08/11/16.
 */
@RestController
public class SampleRestController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/hi", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String hello() {
        return restTemplate.getForObject("http://recommendations-service/hello", String.class);
    }
}
