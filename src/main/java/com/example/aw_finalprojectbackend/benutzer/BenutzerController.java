package com.example.aw_finalprojectbackend.benutzer;

import com.example.aw_finalprojectbackend.sitzung.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.GET, RequestMethod.PUT})
public class BenutzerController {
    private final BenutzerRepository benutzerRepository;
    private final SitzungRepository sitzungRepository;

    @Autowired
    public BenutzerController(BenutzerRepository benutzerRepository, SitzungRepository sitzungRepository) {
        this.benutzerRepository = benutzerRepository;
        this.sitzungRepository = sitzungRepository;
    }

    @PostMapping("/einloggen")
    public ResponseEntity<?> login(@CookieValue(value = "sitzungsId", defaultValue = "") String sitzungsId, @RequestBody LoginRequestDTO login, HttpServletResponse response) {
        Optional<Benutzer> benutzerOptional = benutzerRepository.findByBenutzerNameAndPasswort(login.benutzerName(), login.passwort());

        if (benutzerOptional.isPresent()) {
            Optional<Sitzung> sitzungOptional = sitzungRepository.findBySitzungsIdAndAktivBisAfter(sitzungsId, Instant.now());
            if (sitzungOptional.isEmpty()) {

                Sitzung sitzung = new Sitzung(benutzerOptional.get(), Instant.now().plusSeconds(3 * 60 * 60));
                sitzungRepository.save(sitzung);

                Cookie cookie = new Cookie("sitzungsId", sitzung.getSitzungsId());
                response.addCookie(cookie);

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(Collections.singleton("Erfolgreich eingeloggt."));
            } else return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singleton("Ein Benutzer ist bereits eingeloggt!"));
        } else return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Collections.singleton("Benutzer oder Passwort sind nicht korrekt!"));
    }

    @PostMapping("/ausloggen")
    public ResponseEntity<?> logout(@CookieValue(value = "sitzungsId", defaultValue = "") String sitzungsId, HttpServletResponse response) {
        Optional<Sitzung> sitzungOptional = sitzungRepository.findBySitzungsIdAndAktivBisAfter(sitzungsId, Instant.now());

        if (sitzungOptional.isPresent()) {
            sitzungRepository.delete(sitzungOptional.get());
            Cookie cookie = new Cookie("sitzungsId", "");
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Collections.singleton("Erfolgreich ausgeloggt."));
        } else return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singleton("Kein Benutzer eingeloggt!"));
    }

    @PostMapping("/registrieren")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        Optional<Benutzer> duplicateOptional = benutzerRepository.findByBenutzerName("Neuer Benutzer: " + registerRequestDTO.benutzerName() + " wurde erfolgreich erstellt.");

        if (duplicateOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singleton("Benutzername ist bereits vergeben!"));
        } else {
            if (registerRequestDTO.passwort1().equals(registerRequestDTO.passwort2())) {
                Benutzer erstelleBenutzer = new Benutzer(registerRequestDTO.benutzerName(), registerRequestDTO.passwort1(), registerRequestDTO.vorname(), registerRequestDTO.nachname(), registerRequestDTO.geschlecht());
                benutzerRepository.save(erstelleBenutzer);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(Collections.singleton(registerRequestDTO.benutzerName()));
            } else return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Collections.singleton("Passwörter stimmen nicht überein."));
        }
    }
}
