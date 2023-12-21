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
@RequestMapping("/stimmung")
public class StimmungController {

    @Autowired
    private StimmungService stimmungService;

    @GetMapping()
    public ResponseEntity<List<Stimmung>> getStimmungen(@PathVariable String username) {
        List<Stimmung> stimmungs = stimmungService.getStimmungen(username);
        return ResponseEntity.ok(stimmungs);
    }

    @PostMapping()
    public ResponseEntity<String> createStimmung(@PathVariable String username, @RequestBody Stimmung newStimmung, //StimmungsRequestDTO //Pfadvariablen raus, User kommt über Cookies
                                                 @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {

        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));
        boolean created = stimmungService.createStimmung(username, newStimmung);
        if (created) {
            return ResponseEntity.ok("Stimmung erfolgreich erstellt");
        } else {
            return ResponseEntity.status(404).body("Benutzer nicht gefunden");
        }
    }

    @PutMapping("/{stimmungId}")
    public ResponseEntity<String> editStimmung(@PathVariable Long stimmungId, @PathVariable String username, Stimmung newStimmung, //Pfadvariablen raus, User kommt über Cookies
                                               @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));
        boolean edit = stimmungService.editStimmung(username, stimmungId, newStimmung);
        if (edit) {
            return ResponseEntity.ok("Stimmung geändert");
        } else {
            return ResponseEntity.status(404).body("Stimmung nicht gefunden");
        }

    }

    @DeleteMapping("/{stimmungId}")
    public ResponseEntity<String> deleteStimmung(@PathVariable Long stimmungId, String username,
                                                 @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {

        Boolean deleted = stimmungService.deleteStimmung(username, stimmungId);
        if (deleted) {
            return ResponseEntity.ok("Stimmung gelöscht");
        } else {
            return ResponseEntity.status(404).body("Stimmung nicht gefunden");
        }
    }
}