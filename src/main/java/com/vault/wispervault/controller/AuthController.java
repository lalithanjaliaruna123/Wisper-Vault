package com.vault.wispervault.controller;

import com.vault.wispervault.model.User;
import com.vault.wispervault.repository.UserRepository;
import com.vault.wispervault.service.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest req) {
        if (userRepository.findByUsername(req.getUsername()) != null) {
            return "Username already exists";
        }

        String hashed = passwordEncoder.encode(req.getPassword());
        User user = new User(req.getUsername(), hashed);
        userRepository.save(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest req) {
        User user = userRepository.findByUsername(req.getUsername());
        if (user == null || !passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return "Invalid credentials";
        }

        return jwtUtil.generateToken(user.getUsername());
    }

    // ========== DTO ==========
    static class AuthRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
