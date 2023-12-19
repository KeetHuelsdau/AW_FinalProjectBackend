package com.example.aw_finalprojectbackend.stimmung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/stimmung")
public class StimmungController {

    @Autowired
    private StimmungService stimmungService;
    private BenutzerRepository benutzerRepository;

    @GetMapping()
    public ResponseEntity<List<StimmungEntity>> getStimmungen(@RequestParam(required = false) Long userId,
                                                              @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {

        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));

        List<StimmungEntity> stimmungen = stimmungService.getStimmungen(userId, eingeloggterBenutzer);
        return ResponseEntity.ok(stimmungen);
    }

    @PostMapping()
    public ResponseEntity<StimmungEntity> createStimmung(@RequestBody StimmungDTO stimmungDTO,
                                                         @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {

        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));
        StimmungEntity createdStimmung = stimmungService.createStimmung(stimmungDTO);
        return ResponseEntity.ok(createdStimmung);
    }

    @PutMapping("/{stimmungsId}")
    public ResponseEntity<StimmungEntity> editStimmung(@PathVariable Long stimmungsId, @RequestBody StimmungDTO stimmungDTO,
                                                       @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));
        StimmungEntity editedStimmung = stimmungService.editStimmung(stimmungsId, stimmungDTO);
        return ResponseEntity.ok(editedStimmung);
    }

    @DeleteMapping("/{stimmungsId}")
    public ResponseEntity<Void> deleteStimmung(@PathVariable Long stimmungsId,
                                               @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {

        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));
        stimmungService.deleteStimmung(stimmungsId);
        return ResponseEntity.noContent().build();
    }
}


