package com.example.aw_finalprojectbackend.kryponit;

import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.example.aw_finalprojectbackend.benutzer.BenutzerRepository;
import com.example.aw_finalprojectbackend.benutzer.Benutzer;
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

        return eingeloggterBenutzer.getKryptonite();
    }
    @PostMapping("/kryptonit")
    public Kryptonit erstelleKryptonit(@RequestBody KryptonitRequestDTO kryptonitRequestDTO, @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));

        Kryptonit neuesKryptonit = new Kryptonit(kryptonitRequestDTO.bezeichnung(),eingeloggterBenutzer);

        List<Kryptonit> kryptoniteDesBenutzers = eingeloggterBenutzer.getKryptonite();
        if(kryptoniteDesBenutzers.stream().anyMatch(k -> k.getBezeichnung().equals(neuesKryptonit.getBezeichnung()))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kryponit existiert bereits in der Liste");
        }

        kryptoniteDesBenutzers.add(neuesKryptonit); //Dem Benutzer das neue Kryponit hinzufuegen

        benutzerRepository.save(eingeloggterBenutzer); //Den Benutzer speichern, um das neue Kryptonit hinzuzuspeichern

        return neuesKryptonit;
    }

    @DeleteMapping("/kryptonit/{kryptonitId}")
        public List<Kryptonit> loescheKryptonit(@PathVariable Long kryptonitId, @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));

        //Die Liste der Kryptonite des Benutzers abrufen
        List<Kryptonit> kryponiteDesBenutzers = eingeloggterBenutzer.getKryptonite();

        //Nach dem Kryptonit mit der gegebenen ID suchen und es aus der Liste entfernen
        kryponiteDesBenutzers.removeIf(kryptonit -> kryptonit.getKryptonitId().equals(kryptonitId));

        //den akutalisierten Benutzer speichen, um die Änderungen zu persisitieren
        benutzerRepository.save(eingeloggterBenutzer);

        return eingeloggterBenutzer.getKryptonite();
    }

    @PutMapping("kryptonit/{kryptonitId}")
    public List<Kryptonit> veraendereKryptonitHaeufigkeit(@PathVariable Long kryptonitId, @RequestBody KryptonitVeraendertRequestDTO kryptonitVeraendertRequestDTO, @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));

        List<Kryptonit> kryponiteDesBenutzers = eingeloggterBenutzer.getKryptonite();

        Optional<Kryptonit> veraendertesKryptonitOptional = kryponiteDesBenutzers.stream()
                .filter(kryptonit -> kryptonit.getKryptonitId().equals(kryptonitId))
                .findFirst();

        if(veraendertesKryptonitOptional.isPresent()){
            Kryptonit veraendertesKryptonit = veraendertesKryptonitOptional.get();
            veraendertesKryptonit.setHaeufigkeit(kryptonitVeraendertRequestDTO.veraenderteHaeufigkeit());
            veraendertesKryptonit.setBezeichnung(kryptonitVeraendertRequestDTO.veraenderterName());
        }
        benutzerRepository.save(eingeloggterBenutzer);
        return kryponiteDesBenutzers;
    }


    // TODO : @PutMapping("/kryptonit/{kryptonitId}")
/*    public Kryptonit aktualisiereKryptonitWert(@PathVariable Long id, @RequestParam int neuerKryptonitWert, @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));*/

}

