package com.example.aw_finalprojectbackend.kryponit.kryptonitEintraege;

import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.example.aw_finalprojectbackend.benutzer.BenutzerRepository;
import com.example.aw_finalprojectbackend.kryponit.Kryptonit;
import com.example.aw_finalprojectbackend.kryponit.KryptonitResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.GET, RequestMethod.PUT})
public class KryptonitEintragController {

    BenutzerRepository benutzerRepository;

    @Autowired
    public KryptonitEintragController(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }

    @PostMapping("/kryptonitEintrag/{kryptonitId}")
    public ResponseEntity<?> eintraglisteBearbeiten(@PathVariable Long kryptonitId, @RequestBody kryptonitEintragRequestDTO kryptonitEintragRequestDTO, @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {

        if (eingeloggterBenutzerOptional.isPresent()) {
            Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional.get();

            List<Kryptonit> kryponiteDesBenutzers = eingeloggterBenutzer.getKryptonite();

            Optional<Kryptonit> veraendertesKryptonitOptional = kryponiteDesBenutzers.stream()
                    .filter(kryptonit -> kryptonit.getKryptonitId().equals(kryptonitId))
                    .findFirst();

            if (veraendertesKryptonitOptional.isPresent()) {
                Kryptonit veraendertesKryptonit = veraendertesKryptonitOptional.get();
                List<KryptonitEintrag> eintraege = veraendertesKryptonit.getTaeglicheEintraege();

                //überprüfen, ob ein Eintrag zum heutigen Tag schon existiert, dann wird alter Häufigkeitswert ersetzt
                if (eintraege.stream().anyMatch(eintrag -> eintrag.getZeitpunkt().getDayOfYear() == LocalDate.now().getDayOfYear())) {

                    eintraege.stream()
                            .filter(eintrag -> eintrag.getZeitpunkt().getDayOfYear() == LocalDate.now().getDayOfYear())
                            .findFirst()
                            .get()
                            .setHaeufigkeit(kryptonitEintragRequestDTO.haeufigkeit());

                    benutzerRepository.save(eingeloggterBenutzer);
                    return ResponseEntity.status(HttpStatus.OK).body(new KryptonitResponseDTO(veraendertesKryptonit, "Es gibt schon einen Tageseintrag. Aktueller Kryptonit-Tageseintrag wurde aktualisiert."));
                } else { //erstelle einen neuen Eintrag, falls es zum heutigen Tag noch keinen Eintrag gibt
                    veraendertesKryptonit
                            .getTaeglicheEintraege()
                            .add(new KryptonitEintrag(kryptonitEintragRequestDTO.haeufigkeit(), veraendertesKryptonit));
                    benutzerRepository.save(eingeloggterBenutzer);
                    return ResponseEntity.status(HttpStatus.OK).body(new KryptonitResponseDTO(veraendertesKryptonit, "Tageseintrag wurde erstellt"));
                }

            } else
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonList("Benutzer hat dieses Kryptonit nicht."));
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonList("Login erforderlich"));
    }
}
