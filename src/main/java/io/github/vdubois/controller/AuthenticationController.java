package io.github.vdubois.controller;

import io.github.vdubois.model.AuthTokenDTO;
import io.github.vdubois.model.AuthenticationDTO;
import io.github.vdubois.service.AuthenticationIntegrationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by vdubois on 31/12/16.
 */
@RestController
public class AuthenticationController {

    private AuthenticationIntegrationService authenticationIntegrationService;

    public AuthenticationController(AuthenticationIntegrationService authenticationIntegrationService) {
        this.authenticationIntegrationService = authenticationIntegrationService;
    }

    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AuthTokenDTO authenticate(@RequestBody AuthenticationDTO authenticationDTO) {
        // Authenticate the user
        AuthTokenDTO authToken = authenticationIntegrationService.authenticateUser(authenticationDTO);
        // TODO If authentication fails, return an unauthorized error code

        return authToken;
    }
}
