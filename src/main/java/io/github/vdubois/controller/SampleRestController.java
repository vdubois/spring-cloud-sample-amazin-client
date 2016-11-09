package io.github.vdubois.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.github.vdubois.configuration.RibbonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by vdubois on 08/11/16.
 */
@RestController
@RibbonClient(name = "recommendations-service", configuration = RibbonConfiguration.class)
public class SampleRestController {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallbackHello")
    @RequestMapping(value = "/hi", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String hello() {
        return restTemplate.getForObject("http://recommendations-service/hello", String.class);
    }

    public String fallbackHello() {
        return "Oh hey, nice to see you !";
    }
}
