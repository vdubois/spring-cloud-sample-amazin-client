package io.github.vdubois;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringCloudSampleMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudSampleMicroserviceApplication.class, args);
	}
}
