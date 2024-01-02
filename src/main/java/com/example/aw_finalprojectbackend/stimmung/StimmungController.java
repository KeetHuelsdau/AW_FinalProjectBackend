package com.example.aw_finalprojectbackend.stimmung;

import com.example.aw_finalprojectbackend.ListenSammlung;
import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.example.aw_finalprojectbackend.benutzer.BenutzerRepository;
import com.example.aw_finalprojectbackend.kryponit.Kryptonit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.GET, RequestMethod.PUT})
public class StimmungController {

    private final StimmungService stimmungService;
    private final BenutzerRepository benutzerRepository;

    @Autowired

    public StimmungController(StimmungService stimmungService, BenutzerRepository benutzerRepository) {
        this.stimmungService = stimmungService;
        this.benutzerRepository = benutzerRepository;
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

            List<Stimmung> stimmungenDesBenutzers = eingeloggterBenutzer.getStimmungen();
            Optional<Stimmung> stimmungInnerhalbDerLetztenZweiStundenOptional = stimmungenDesBenutzers
                    .stream()
                    .filter(stimmung -> stimmung
                            .getErstellungszeit()
                            .plusHours(2)
                            .isAfter(LocalDateTime.now())
                    ).findFirst();
            if (stimmungInnerhalbDerLetztenZweiStundenOptional.isPresent()) {
                Stimmung stimmungInnerhalbDerLetztenZweiStunden = stimmungInnerhalbDerLetztenZweiStundenOptional.get();
                stimmungInnerhalbDerLetztenZweiStunden.setRating(stimmungDTO.rating());
                benutzerRepository.save(eingeloggterBenutzer);
                String ablaufZeit = stimmungInnerhalbDerLetztenZweiStunden.getErstellungszeit().plusHours(2).format(DateTimeFormatter.ofPattern("HH:mm"));
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new StimmungResponseDTO(stimmungInnerhalbDerLetztenZweiStunden, ablaufZeit,
                                "Die aktuelle Stimmung wurde geändert. Du kannst erst um " +
                                        ablaufZeit +
                                " wieder eine neue Stimmung hinzufügen!"));
            } else {
                Stimmung neueStimmung = stimmungService.erstelleStimmung(eingeloggterBenutzer, stimmungDTO);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new StimmungResponseDTO(neueStimmung, "","Stimmung erfolgreich erstellt"));
            }

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonList("Login erforderlich"));
        }
    }

    @PutMapping("/stimmung/{stimmungId}")
    public ResponseEntity<List<?>> editiereKommentar(@PathVariable Long stimmungId, @RequestBody StimmungskommentarRequestDTO stimmungskommentarRequestDTO,
                                                    @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        if (eingeloggterBenutzerOptional.isPresent()) {
            Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional.get();
            Stimmung bearbeiteteStimmung = stimmungService.editiereStimmung(eingeloggterBenutzer, stimmungId, stimmungskommentarRequestDTO);

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
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(Collections.singletonList("Stimmung gelöscht"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stimmung nicht gefunden");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonList("Login erforderlich"));
        }
    }
}