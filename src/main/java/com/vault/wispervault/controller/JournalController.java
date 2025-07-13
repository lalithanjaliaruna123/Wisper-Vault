package com.vault.wispervault.controller;

import com.vault.wispervault.model.JournalEntry;
import com.vault.wispervault.service.JournalService;
import com.vault.wispervault.service.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/journal")
public class JournalController {

    private final JournalService journalService;
    private final JwtUtil jwtUtil;

    public JournalController(JournalService journalService, JwtUtil jwtUtil) {
        this.journalService = journalService;
        this.jwtUtil = jwtUtil;
    }

    private String extractUsername(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7); // Skip "Bearer "
        return jwtUtil.extractUsername(token);
    }

    @PostMapping("/create")
    public JournalEntry create(@RequestBody JournalRequest requestBody, HttpServletRequest request) throws Exception {
        String username = extractUsername(request);
        return journalService.createEntry(username, requestBody.getPlainText(), requestBody.getPassword());
    }

    @GetMapping("/all")
    public List<JournalEntry> getAll(HttpServletRequest request) {
        String username = extractUsername(request);
        return journalService.getAllEntries(username);
    }

    @GetMapping("/{id}")
    public Optional<JournalEntry> getById(@PathVariable Long id) {
        return journalService.getEntryById(id);
    }

    @PostMapping("/decrypt/{id}")
    public String decrypt(@PathVariable Long id, @RequestBody PasswordRequest body) throws Exception {
        return journalService.decryptEntry(id, body.getPassword());
    }

    @PutMapping("/update/{id}")
    public JournalEntry update(@PathVariable Long id,
                               @RequestBody JournalRequest requestBody,
                               HttpServletRequest request) throws Exception {
        String username = extractUsername(request); // this line could be breaking too
        return journalService.updateEntry(id, requestBody.getPlainText(), requestBody.getPassword());
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, HttpServletRequest request) {
        String username = extractUsername(request);
        journalService.deleteEntry(username, id);
    }

    // ========= DTOs ==========

    public static class JournalRequest {
        private String plainText;
        private String password;

        public String getPlainText() { return plainText; }
        public void setPlainText(String plainText) { this.plainText = plainText; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class PasswordRequest {
        private String password;

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
