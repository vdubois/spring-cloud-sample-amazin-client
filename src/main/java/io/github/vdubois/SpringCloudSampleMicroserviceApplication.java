package io.github.vdubois;

import io.github.vdubois.configuration.AppConfiguration;
import io.github.vdubois.configuration.RibbonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@ImportAutoConfiguration(value = {AppConfiguration.class})
@RibbonClient(name = "recommendations-service", configuration = RibbonConfiguration.class)
public class SpringCloudSampleMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudSampleMicroserviceApplication.class, args);
	}
}
