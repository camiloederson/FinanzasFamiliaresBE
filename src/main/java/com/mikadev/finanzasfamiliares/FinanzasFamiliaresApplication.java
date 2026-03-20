package com.mikadev.finanzasfamiliares;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FinanzasFamiliaresApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinanzasFamiliaresApplication.class, args);
//         UTILITY TO ENCODE A RAW PASSWORD
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String rawPassword = "maria123456";
//        String encodedPassword = encoder.encode(rawPassword);
//        System.out.println("Encoded password: " + encodedPassword);
    }

}
