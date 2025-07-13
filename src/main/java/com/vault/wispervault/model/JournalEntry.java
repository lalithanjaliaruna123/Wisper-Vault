package com.vault.wispervault.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "journal_entry")
public class JournalEntry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "encrypted_text", nullable = false, columnDefinition = "TEXT")
    private String encryptedText;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public JournalEntry() {}
    public JournalEntry(String encryptedText, LocalDateTime ts, User u) {
        this.encryptedText = encryptedText;
        this.timestamp = ts;
        this.user = u;
    }

    public Long getId() { return id; }
    public String getEncryptedText() { return encryptedText; }
    public void setEncryptedText(String e) { this.encryptedText = e; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime t) { this.timestamp = t; }
    public User getUser() { return user; }
}
