package com.example.leucine.config;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        // Hashed password from the database
        String storedHashedPassword = "$2a$10$5B1t136wmv./ypOUcwrqQuE2B472XM93fSFFcbKoKNmxJQVkv/aai";
        
        // Raw password entered by the user
        String rawPassword = "password123";

        // Check if the password matches
        boolean isMatch = passwordEncoder.matches(rawPassword, storedHashedPassword);
        System.out.println("Password matches: " + isMatch);
    }
}
