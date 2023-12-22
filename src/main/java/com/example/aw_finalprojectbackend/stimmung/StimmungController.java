package com.example.aw_finalprojectbackend.stimmung;

import com.example.aw_finalprojectbackend.ListenSammlung;
import com.example.aw_finalprojectbackend.benutzer.Benutzer;
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


    private final  StimmungService stimmungService;

    @Autowired
    public StimmungController(StimmungService stimmungService){
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
        if (eingeloggterBenutzerOptional.isPresent()) {
            Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional.get();
            Stimmung neueStimmung = stimmungService.erstelleStimmung(eingeloggterBenutzer, stimmungDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new StimmungResponseDTO(neueStimmung, "Stimmung erfolgreich erstellt"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonList("Login erforderlich"));
        }
    }


    @PutMapping("/stimmung/{stimmungId}")
    public ResponseEntity<List<?>> editiereStimmung(@PathVariable Long stimmungId, @RequestBody StimmungRequestDTO stimmungRequestDTO,
                                                    @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        if (eingeloggterBenutzerOptional.isPresent()) {
            Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional.get();
            Stimmung bearbeiteteStimmung = stimmungService.editiereStimmung(eingeloggterBenutzer, stimmungId, stimmungRequestDTO);

            if (bearbeiteteStimmung != null) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(Collections.singletonList("Stimmung geändert"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonList("Stimmung nicht gefunden"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonList("Login erforderlich"));
        }
    }

    @DeleteMapping("/stimmung/{stimmungId}")
    public ResponseEntity<?> loescheStimmung(@PathVariable Long stimmungId,
                                             @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        if (eingeloggterBenutzerOptional.isPresent()) {
            Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional.get();
            boolean deleted = stimmungService.loescheStimmung(stimmungId, eingeloggterBenutzer);
            if (deleted) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(Collections.singletonList("Stimmung gelöscht").toString());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stimmung nicht gefunden");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonList("Login erforderlich"));
        }
    }

}