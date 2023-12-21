package com.example.aw_finalprojectbackend.stimmung;

import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<List<Stimmung>> erhalteAlleStimmungen(
                                                        @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));

        List<Stimmung> stimmungs = stimmungService.getStimmungen(eingeloggterBenutzer.getBenutzerName());
        return ResponseEntity.ok(stimmungs);
    }

    @PostMapping("/stimmung")
    public ResponseEntity<String> erstelleStimmung(@RequestBody StimmungRequestDTO stimmungDTO,
                                                   @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {

        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));

        boolean created = stimmungService.createStimmung(eingeloggterBenutzer.getBenutzerName(), stimmungDTO);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Stimmung erfolgreich erstellt");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Benutzer nicht gefunden");
        }
    }

    @PutMapping("/stimmung/{stimmungId}")
    public ResponseEntity<String> editiereStimmung(@PathVariable Long stimmungId, StimmungResponseDTO stimmungDTO,
                                                   @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));

        boolean edit = stimmungService.editStimmung(eingeloggterBenutzer.getBenutzerName(), stimmungId, stimmungDTO);
        if (edit) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Stimmung geändert");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stimmung nicht gefunden");
        }

    }

    @DeleteMapping("/stimmung/{stimmungId}")
    public ResponseEntity<String> loescheStimmung(@PathVariable Long stimmungId,
                                                  @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));

        boolean deleted = stimmungService.deleteStimmung(stimmungId,eingeloggterBenutzer.getBenutzerName());
        if (deleted) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Stimmung gelöscht");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stimmung nicht gefunden");
        }
    }
}