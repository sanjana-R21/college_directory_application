package com.example.leucine.config;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "password123";
        String hashedPassword = passwordEncoder.encode(rawPassword);

        System.out.println("Hashed password for 'password123': " + hashedPassword);
    }
}
