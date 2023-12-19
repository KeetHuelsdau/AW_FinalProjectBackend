package com.example.aw_finalprojectbackend.kryponit;

import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.example.aw_finalprojectbackend.benutzer.BenutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class KryptonitController {

    private final BenutzerRepository benutzerRepository;

    @Autowired
    public KryptonitController(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }

    @GetMapping("/kryptonite")
    public List<Kryptonit> erhalteAlleKryptonite(@ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));

        List<Kryptonit> kryptoniten = eingeloggterBenutzer.getKryptonite();

        return kryptoniten;
    }

    @PostMapping("/kryptonit")
    public void erstelleKryptonit(@RequestBody Kryptonit kryptonit, @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));

        kryptonit.setBenutzer(eingeloggterBenutzer); //Das Kryptonit dem Benutzer zuweisen
        List<Kryptonit> kryptoniteDesBenutzers = eingeloggterBenutzer.getKryptonite();
        kryptoniteDesBenutzers.add(kryptonit); //Dem Benutzer das neue Kryponit hinzufuegen

        eingeloggterBenutzer.setKryptonite(kryptoniteDesBenutzers); //Aktualisierte Kryptonitliste dem Benutzer setzen

        benutzerRepository.save(eingeloggterBenutzer); //Den Benutzer speichern, um das neue Kryptonit hinzuzuspeichern
    }

    @DeleteMapping("/kryptonit/{kryptonitId}")
    public void loescheKryptonit(@PathVariable Long kryptonitId, @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));

        //Die Liste der Kryptonite des Benutzers abrufen
        List<Kryptonit> kryponiteDesBenutzers = eingeloggterBenutzer.getKryptonite();

        //Nach dem Kryptonit mit der gegebenen ID suchen und es aus der Liste entfernen
        kryponiteDesBenutzers.removeIf(kryptonit -> kryptonit.getKryptonitId().equals(kryptonitId));

        //die aktualisierte Kryptonitliste dem Benutzer setzen
        eingeloggterBenutzer.setKryptonite(kryponiteDesBenutzers);

        //den akutalisierten Benutzer speichen, um die Änderungen zu persisitieren
        benutzerRepository.save(eingeloggterBenutzer);
    }

    // TODO : @PutMapping("/kryptonit/{kryptonitId}")
/*    public Kryptonit aktualisiereKryptonitWert(@PathVariable Long id, @RequestParam int neuerKryptonitWert, @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));*/

}

