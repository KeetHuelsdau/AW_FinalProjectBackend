package com.example.aw_finalprojectbackend.balsam.balsamEintraege;

import com.example.aw_finalprojectbackend.balsam.Balsam;
import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.example.aw_finalprojectbackend.benutzer.BenutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController

public class BalsamEintragController {

    BenutzerRepository benutzerRepository;

    @Autowired
    public BalsamEintragController(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }

    @PostMapping("/balsam/{balsamId}/eintrag")
    public ResponseEntity<?> eintraglisteBearbeiten(@PathVariable Long balsamId, @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {

        if (eingeloggterBenutzerOptional.isPresent()) {
            Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional.get();

            List<Balsam> balsameDesBenutzers = eingeloggterBenutzer.getBalsame();

            Optional<Balsam> veraendertesBalsamOptional = balsameDesBenutzers.stream()
                    .filter(balsam -> balsam.getBalsamId().equals(balsamId))
                    .findFirst();

            if (veraendertesBalsamOptional.isPresent()) {
                Balsam veraendertesBalsam = veraendertesBalsamOptional.get();
                List<BalsamEintrag> eintraege = veraendertesBalsam.getTaeglicheEintraege();

                //überprüfen, ob ein Eintrag zum heutigen Tag schon existiert, dann wird alter Häufigkeitswert ersetzt
                if(eintraege.stream().anyMatch(eintrag -> eintrag.getZeitpunkt().getDayOfYear() == LocalDate.now().getDayOfYear())) {
                    eintraege
                            .stream()
                            .filter(eintrag -> eintrag.getZeitpunkt().getDayOfYear() == LocalDate.now().getDayOfYear())
                            .findFirst()
                            .get()
                            .toggleAktiv();
                } else { //erstelle einen neuen Eintrag, falls es zum heutigen Tag noch keinen Eintrag gibt
                    veraendertesBalsam
                            .getTaeglicheEintraege()
                            .add(new BalsamEintrag(veraendertesBalsam));
                }
                benutzerRepository.save(eingeloggterBenutzer);
                return ResponseEntity.status(HttpStatus.OK).body(veraendertesBalsam);
            } else return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonList("Benutzer hat diesen Balsam nicht."));
        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonList("Login erforderlich"));
    }
}
