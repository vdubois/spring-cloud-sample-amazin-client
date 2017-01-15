package io.github.vdubois.configuration;

import io.github.vdubois.config.JsonWebTokenSecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Created by vdubois on 30/12/16.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends JsonWebTokenSecurityConfiguration {

    @Override
    protected void setupAuthorization(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                // allow anonymous access to /authenticate endpoint
                .antMatchers("/api/v*/authenticate").permitAll()

                // allow anonymous to common static resources
                .antMatchers(HttpMethod.GET, "/", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js", "/api/v*/service-info")
                .permitAll()

                .antMatchers(HttpMethod.OPTIONS).permitAll()

                // authenticate all other requests
                .anyRequest().authenticated();
    }
}
