package com.example.zendesktest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@RestController
@RequestMapping("/jwt")
public class JWTToken {

    @PostMapping
    public ResponseEntity<Object> createJWT(@RequestParam(name = "user_token") String secret) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JSONObject payload = new JSONObject();
        payload.put("email", "softtourtravel@gmail.com");
        payload.put("name", "soft tour");
        payload.put("iat", nowMillis);
        payload.put("jti", 123456789);

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setPayload(payload.toJSONString())
                .signWith(signatureAlgorithm, signingKey);

        //Builds the JWT and serializes it to a compact, URL-safe string
        JSONObject response = new JSONObject();
        response.put("jwt", builder.compact());
        return ResponseEntity.ok(response);
    }

    public Claims decodeJWT(String jwt, String secret) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

}
