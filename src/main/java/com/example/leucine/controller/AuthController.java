package com.example.leucine.controller;

import com.example.leucine.model.User;
import com.example.leucine.service.UserService;
import com.example.leucine.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginRequest) throws AuthenticationException {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        // Authenticate the user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // Generate JWT token
        String token = jwtUtil.generateToken(username);

        // Fetch the user by username to get the userId
        User user = userService.getUserByUsername(username);

        // Prepare the response with token and userId
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", user.getId());  // Add userId to the response
        return response;
    }
}
