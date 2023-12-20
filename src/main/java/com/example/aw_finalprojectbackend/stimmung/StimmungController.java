package com.example.aw_finalprojectbackend.stimmung;

import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/stimmung")
public class StimmungController {

    @Autowired
    private StimmungService stimmungService;

    @GetMapping()

    public ResponseEntity<Optional<Benutzer>> getStimmungen(@RequestParam(required = false) String benutzername,
                                                            @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {

        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));

        Optional<Benutzer> stimmungen = stimmungService.getStimmungen(benutzername);

        return ResponseEntity.ok(stimmungen);
    }

    @PostMapping()
    public ResponseEntity<Stimmung> createStimmung(@RequestBody StimmungResponseDTO stimmungDTO,
                                                   @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {

        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));
        Stimmung createdStimmung = stimmungService.createStimmung(stimmungDTO);
        return ResponseEntity.ok(createdStimmung);
    }

    @PutMapping("/{stimmungId}")
    public ResponseEntity<Stimmung> editStimmung(@PathVariable Long stimmungId, @RequestBody StimmungResponseDTO stimmungDTO,
                                                 @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));
        Stimmung editedStimmung = stimmungService.editStimmung(stimmungId, stimmungDTO);

        return ResponseEntity.ok(editedStimmung);
    }

    @DeleteMapping("/{stimmungId}")
    public ResponseEntity<Void> deleteStimmung(@PathVariable Long stimmungId,
                                               @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {

        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));
        stimmungService.deleteStimmung(stimmungId);
        return ResponseEntity.noContent().build();
    }
}


