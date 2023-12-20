package com.example.aw_finalprojectbackend.sitzung;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface SitzungRepository extends JpaRepository<Sitzung, Long> {
    Optional<Sitzung> findBySitzungsIdAndAktivBisAfter(String sitzungsId, Instant expiresAt);
}
