package io.github.vdubois.security.service;

import io.github.vdubois.security.model.AuthTokenDTO;
import io.github.vdubois.security.model.AuthenticationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by vdubois on 31/12/16.
 */
@Service
public class AuthenticationIntegrationService {

    @LoadBalanced
    @Autowired
    private RestTemplate restTemplate;

    public AuthTokenDTO authenticateUser(AuthenticationDTO authenticationDTO) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://authentication-service/authenticate");
        return restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, new HttpEntity<>(authenticationDTO), AuthTokenDTO.class).getBody();
    }
}
