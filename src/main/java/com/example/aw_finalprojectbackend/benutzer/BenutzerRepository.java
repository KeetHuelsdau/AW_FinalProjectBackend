package com.example.aw_finalprojectbackend.benutzer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BenutzerRepository extends JpaRepository<Benutzer, Long> {
    Optional<Benutzer> findByBenutzerNameAndPasswort(String username, String password);
    Optional<Benutzer> findByBenutzerName(String username);
}
