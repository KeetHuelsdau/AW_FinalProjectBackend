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

    @Autowired
    public StimmungController(StimmungService stimmungService) {
        this.stimmungService = stimmungService;
    }

    @GetMapping()
    public ResponseEntity<List<Stimmung>> getStimmungen(@RequestParam String username) {
        List<Stimmung> stimmungs = stimmungService.getStimmungen(username);
        return ResponseEntity.ok(stimmungs);
    }

    @PostMapping()
    public ResponseEntity<String> createStimmung(@RequestBody StimmungRequestDTO stimmungDTO,
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

    @PutMapping("/{stimmungId}")
    public ResponseEntity<String> editStimmung(@PathVariable Long stimmungId, StimmungResponseDTO stimmungDTO,
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

    @DeleteMapping("/{stimmungId}")
    public ResponseEntity<String> deleteStimmung(@PathVariable Long stimmungId, String username,
                                                 @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        boolean deleted = stimmungService.deleteStimmung(stimmungId,username);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Stimmung gelöscht");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stimmung nicht gefunden");
        }
    }
}