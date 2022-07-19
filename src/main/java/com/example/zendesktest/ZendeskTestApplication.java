package com.example.zendesktest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.zendesk.client.v2.Zendesk;


@SpringBootApplication
public class ZendeskTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZendeskTestApplication.class, args);

        String secret = "8yK5AA2JSaDDpP4uwPIuWhxHsvVmUuh4xHCypje9yv2jaNMg";

        JWTToken jwtToken = new JWTToken();
        ResponseEntity<Object> jwt = jwtToken.createJWT(secret);
        System.out.println("JWT: " + jwt);
    }

}
