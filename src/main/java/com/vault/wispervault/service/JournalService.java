package com.vault.wispervault.service;

import com.vault.wispervault.model.JournalEntry;
import com.vault.wispervault.model.User;
import com.vault.wispervault.repository.JournalRepository;
import com.vault.wispervault.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalService {

    private final JournalRepository journalRepository;
    private final UserRepository userRepository;

    public JournalService(JournalRepository journalRepository, UserRepository userRepository) {
        this.journalRepository = journalRepository;
        this.userRepository = userRepository;
    }

    public JournalEntry createEntry(String username, String plainText, String password) throws Exception {
        User user = userRepository.findByUsername(username);
        String encrypted = EncryptionUtil.encrypt(plainText, password);
        JournalEntry entry = new JournalEntry(encrypted, LocalDateTime.now(), user);
        return journalRepository.save(entry);
    }

    public List<JournalEntry> getAllEntries(String username) {
        User user = userRepository.findByUsername(username);
        return journalRepository.findByUser(user);
    }

    public Optional<JournalEntry> getEntryById(Long id) {
        return journalRepository.findById(id);
    }

    public String decryptEntry(Long id, String password) throws Exception {
        JournalEntry entry = journalRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Journal entry not found"));
        return EncryptionUtil.decrypt(entry.getEncryptedText(), password);
    }
    public JournalEntry updateEntry(Long id, String newPlainText, String password) throws Exception {
        JournalEntry entry = journalRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Entry not found"));

        String encrypted = EncryptionUtil.encrypt(newPlainText, password);
        entry.setEncryptedText(encrypted);
        entry.setTimestamp(LocalDateTime.now()); // update timestamp if needed

        return journalRepository.save(entry);
    }
    
    public void deleteEntry(String username, Long id) {
        JournalEntry entry = journalRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Entry not found"));

        if (!entry.getUser().getUsername().equals(username)) {
            throw new SecurityException("Unauthorized to delete this entry");
        }

        journalRepository.delete(entry);
    }
}
