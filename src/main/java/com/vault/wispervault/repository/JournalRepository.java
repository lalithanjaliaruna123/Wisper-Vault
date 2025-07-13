package com.vault.wispervault.repository;

import com.vault.wispervault.model.JournalEntry;
import com.vault.wispervault.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JournalRepository extends JpaRepository<JournalEntry, Long> {
    List<JournalEntry> findByUser(User user);
}
