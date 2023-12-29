package com.example.aw_finalprojectbackend.balsam;

import com.example.aw_finalprojectbackend.balsam.balsamEintraege.BalsamEintrag;
import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.example.aw_finalprojectbackend.benutzer.BenutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.GET, RequestMethod.PUT})
public class BalsamController {

    private final BenutzerRepository benutzerRepository;

    @Autowired
    public BalsamController(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }

    @PostMapping("/balsam")
    public ResponseEntity<?> erstelleNeuenBalsam(@RequestBody BalsamRequestDTO balsamRequestDTO, @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        if (eingeloggterBenutzerOptional.isPresent()) {
            Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional.get();
            List<Balsam> balsameDesBenutzers = eingeloggterBenutzer.getBalsame();
            if (balsameDesBenutzers.stream().anyMatch(k -> k.getBezeichnung().equalsIgnoreCase(balsamRequestDTO.bezeichnung()))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList("Balsam existiert bereits in der Liste"));
            }
            //Erstelle neuen Balsam
            Balsam neuerBalsam = new Balsam(balsamRequestDTO.bezeichnung(), eingeloggterBenutzer);

            //Füge den Balsam ohne Aktivitätsflag hinzu
            balsameDesBenutzers.add(neuerBalsam);
            benutzerRepository.save(eingeloggterBenutzer);
            return ResponseEntity.status(HttpStatus.CREATED).body(neuerBalsam);
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonList("Login erforderlich"));
    }

    @GetMapping("/balsame")
    public ResponseEntity<List<?>> erhalteAlleBalsame(@ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        if (eingeloggterBenutzerOptional.isPresent()) {
            Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional.get();

            List<Balsam> balsame = eingeloggterBenutzer.getBalsame();
            //Gehe durch die Liste und setze eine Eigenschaft für die Farbe basierend auf den aktiv Status
            balsame.forEach(balsam -> {
                boolean aktiv =  balsam.getTaeglicheEintraege()
                                .stream()
                                .anyMatch(BalsamEintrag::getAktiv);
                balsam.setFarbe(aktiv ? "green" : "red");
            });
            return ResponseEntity.ok(balsame);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonList("Login erforderlich"));
        }
    }

    @DeleteMapping("/balsam/{balsamId}")
    public ResponseEntity<?> loescheBalsam(@PathVariable Long balsamId, @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        if (eingeloggterBenutzerOptional.isPresent()) {
            Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional.get();

            //Die Liste der Balsame des Benutzers abrufen
            List<Balsam> balsameDesBenutzers = eingeloggterBenutzer.getBalsame();

            //Nach dem Balsam mit der gegebenen ID suchen und es aus der Liste entfernen
            boolean gefunden = balsameDesBenutzers.removeIf(balsam -> balsam.getBalsamId().equals(balsamId));
            if (gefunden) {
                benutzerRepository.save(eingeloggterBenutzer);
                return ResponseEntity.ok(eingeloggterBenutzer.getBalsame());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonList("Balsam existiert nicht"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonList("Login erforderlich"));
        }
    }
}


