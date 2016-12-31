package io.github.vdubois.service;

import io.github.vdubois.dto.AuthTokenDetailsDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * Created by vdubois on 30/12/16.
 */
@Service
@Log
public class JSONWebTokenConsumerService {

    private SignatureAlgorithm signatureAlgorithm;

    private Key secretKey;

    public JSONWebTokenConsumerService() {
        String encodedKey = "L7A/6zARSkK1j7Vd5SDD9pSSqZlqF7mAhiOgRbgv9Smce6tf4cJnvKOjtKPxNNnWQj+2lQEScm3XIUjhW+YVZg==";
        secretKey = deserializeKey(encodedKey);
    }

    public AuthTokenDetailsDTO parseAndValidate(String token) {
        AuthTokenDetailsDTO authTokenDetailsDTO = null;
        try {
            Claims claims = Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody();
            String userId = claims.getSubject();
            String email = (String) claims.get("email");
            List<String> roleNames = (List<String>) claims.get("roles");
            Date expirationDate = claims.getExpiration();

            authTokenDetailsDTO = new AuthTokenDetailsDTO();
            authTokenDetailsDTO.setUserId(userId);
            authTokenDetailsDTO.setEmail(email);
            authTokenDetailsDTO.setRoleNames(roleNames);
            authTokenDetailsDTO.setExpirationDate(expirationDate);
        } catch (JwtException jwtException) {
            log.severe(jwtException.getMessage());
            jwtException.printStackTrace();
        }
        return authTokenDetailsDTO;
    }

    private Key getSecretKey() {
        return secretKey;
    }

    public SignatureAlgorithm getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    private Key deserializeKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        Key key = new SecretKeySpec(decodedKey, getSignatureAlgorithm().getJcaName());
        return key;
    }
}
