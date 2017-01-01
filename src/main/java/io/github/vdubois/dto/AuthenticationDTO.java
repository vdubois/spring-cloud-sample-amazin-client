package io.github.vdubois.dto;

import lombok.Data;

/**
 * Created by vdubois on 31/12/16.
 */
@Data
public class AuthenticationDTO {
    private String email;
    private String password;
}