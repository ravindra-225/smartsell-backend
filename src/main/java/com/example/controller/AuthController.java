package com.example.controller;

import com.example.dtos.*;
import com.example.models.Users;
import com.example.repository.*;
import com.example.security.JWTUtil;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	 private final UserRepository userRepository;
	    private final JWTUtil jwtUtil;
	    @Autowired
	    private UserDetailsService userDetailsService; // Inject UserDetailsService
	    private final AuthenticationManager authenticationManager;
	    private final BCryptPasswordEncoder passwordEncoder;

	    public AuthController(UserRepository userRepository, JWTUtil jwtUtil,
	                          AuthenticationManager authenticationManager,
	                          BCryptPasswordEncoder passwordEncoder) {
	        this.userRepository = userRepository;
	        this.jwtUtil = jwtUtil;
	        this.authenticationManager = authenticationManager;
	        this.passwordEncoder = passwordEncoder;
	    }
    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> register(@Valid @RequestBody Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully!");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
