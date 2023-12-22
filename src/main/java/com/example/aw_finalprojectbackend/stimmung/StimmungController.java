package com.example.aw_finalprojectbackend.stimmung;

import com.example.aw_finalprojectbackend.ListenSammlung;
import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.example.aw_finalprojectbackend.benutzer.BenutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class StimmungController {

    @Autowired
    private StimmungService stimmungService;

    @Autowired
    public StimmungController(StimmungService stimmungService) {
        this.stimmungService = stimmungService;
    }

    @GetMapping("/stimmungen")
    public ResponseEntity<?> erhalteAlleStimmungen(
            @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        if (eingeloggterBenutzerOptional.isPresent()) {
            Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional.get();
            return ResponseEntity.ok(new ListenSammlung(eingeloggterBenutzer.getStimmungen()));

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonList("Login erforderlich"));
        }
    }

    @PostMapping("/stimmung")
    public ResponseEntity<?> erstelleStimmung(@RequestBody StimmungRequestDTO stimmungDTO,
                                                                @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));
        Stimmung neueStimmung = stimmungService.erstelleStimmung(eingeloggterBenutzer, stimmungDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new StimmungResponseDTO(neueStimmung, "Stimmung erfolgreich erstellt"));
    }

    @PutMapping("/stimmung/{stimmungId}")
    public ResponseEntity<List<?>> editiereStimmung(@PathVariable Long stimmungId, StimmungRequestDTO stimmungDTO,
                                                         @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));

        Stimmung neueStimmung = stimmungService.editiereStimmung(eingeloggterBenutzer, stimmungId, stimmungDTO);
        if (neueStimmung != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Collections.singletonList("Stimmung geändert"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonList("Stimmung nicht gefunden"));
        }

    }

    @DeleteMapping("/stimmung/{stimmungId}")
    public ResponseEntity<?> loescheStimmung(@PathVariable Long stimmungId,
                                                  @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));

        boolean deleted = stimmungService.loescheStimmung(stimmungId, eingeloggterBenutzer);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Collections.singletonList("Stimmung gelöscht"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stimmung nicht gefunden");
        }
    }

}