package com.vault.wispervault.controller;

import com.vault.wispervault.model.User;
import com.vault.wispervault.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@RequestBody User u) {
        if (userRepo.findByUsername(u.getUsername()) != null) {
            return "❌ Username already exists!";
        }
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        userRepo.save(u);
        return "✅ User registered successfully!";
    }

    @PostMapping("/login")
    public String login(@RequestBody User u) {
        User db = userRepo.findByUsername(u.getUsername());
        if (db == null || !passwordEncoder.matches(u.getPassword(), db.getPassword())) {
            return "❌ Invalid username or password!";
        }
        return "✅ Login success! Use Basic Auth for future calls.";
    }
}
