package com.example.aw_finalprojectbackend.kryponit;

import com.example.aw_finalprojectbackend.ListenSammlung;
import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.example.aw_finalprojectbackend.benutzer.BenutzerRepository;
import com.example.aw_finalprojectbackend.kryponit.kryptonitEintraege.KryptonitEintrag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Collections;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class KryptonitController {

    private final BenutzerRepository benutzerRepository;

    @Autowired
    public KryptonitController(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }

    @GetMapping("/kryptonite")
    public ResponseEntity<?> erhalteAlleKryptonite(@ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        if (eingeloggterBenutzerOptional.isPresent()) {
            Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional.get();
            return ResponseEntity.ok(new ListenSammlung(eingeloggterBenutzer.getKryptonite()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonList("Login erforderlich"));
        }
    }

    @PostMapping("/kryptonit")
    public ResponseEntity<?> erstelleKryptonit(@RequestBody KryptonitRequestDTO kryptonitRequestDTO, @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        if (eingeloggterBenutzerOptional.isPresent()) {
            Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional.get();
            Kryptonit neuesKryptonit = new Kryptonit(kryptonitRequestDTO.bezeichnung(), eingeloggterBenutzer);

            List<Kryptonit> kryptoniteDesBenutzers = eingeloggterBenutzer.getKryptonite();
            if (kryptoniteDesBenutzers.stream().anyMatch(k -> k.getBezeichnung().equalsIgnoreCase(neuesKryptonit.getBezeichnung()))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList("Kryptonit existiert bereits in der Liste"));
            }

            kryptoniteDesBenutzers.add(neuesKryptonit); //Dem Benutzer das neue Kryponit hinzufuegen

            benutzerRepository.save(eingeloggterBenutzer); //Den Benutzer speichern, um das neue Kryptonit zur DB hinzuzuspeichern

            return ResponseEntity.status(HttpStatus.CREATED).body(neuesKryptonit);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonList("Login erforderlich"));
        }
    }

    @DeleteMapping("/kryptonit/{kryptonitId}")
    public ResponseEntity<?> loescheKryptonit(@PathVariable Long kryptonitId, @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        if (eingeloggterBenutzerOptional.isPresent()) {
            Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional.get();
            //Die Liste der Kryptonite des Benutzers abrufen
            List<Kryptonit> kryponiteDesBenutzers = eingeloggterBenutzer.getKryptonite();

            //Nach dem Kryptonit mit der gegebenen ID suchen und es aus der Liste entfernen
            boolean gefunden = kryponiteDesBenutzers.removeIf(kryptonit -> kryptonit.getKryptonitId().equals(kryptonitId));
            if (gefunden) {
                benutzerRepository.save(eingeloggterBenutzer);
                return ResponseEntity.ok(eingeloggterBenutzer.getKryptonite());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonList("Kryptonit existiert nicht"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonList("Login erforderlich"));
        }
    }

    @PutMapping("kryptonit/{kryptonitId}")
    public ResponseEntity<List<?>> veraendereKryptonitName(@PathVariable Long kryptonitId, @RequestBody KryptonitVeraendertRequestDTO kryptonitVeraendertRequestDTO, @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        if (eingeloggterBenutzerOptional.isPresent()) {
            Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional.get();
            List<Kryptonit> kryponiteDesBenutzers = eingeloggterBenutzer.getKryptonite();

            Optional<Kryptonit> veraendertesKryptonitOptional = kryponiteDesBenutzers.stream()
                    .filter(kryptonit -> kryptonit.getKryptonitId().equals(kryptonitId))
                    .findFirst();

            if (veraendertesKryptonitOptional.isPresent()) {
                Kryptonit veraendertesKryptonit = veraendertesKryptonitOptional.get();
                veraendertesKryptonit.setBezeichnung(kryptonitVeraendertRequestDTO.veraenderterName());
                benutzerRepository.save(eingeloggterBenutzer);
                return ResponseEntity.ok(kryponiteDesBenutzers);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonList("Kryptonit nicht vorhanden oder aktueller Benutzer hat keinen Zugriff auf dieses Kryptonit!"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonList("Login erforderlich"));
        }
    }
}