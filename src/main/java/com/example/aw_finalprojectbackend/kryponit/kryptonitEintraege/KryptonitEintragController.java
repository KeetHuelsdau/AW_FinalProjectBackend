package com.example.aw_finalprojectbackend.kryponit.kryptonitEintraege;

import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.example.aw_finalprojectbackend.benutzer.BenutzerRepository;
import com.example.aw_finalprojectbackend.kryponit.Kryptonit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class KryptonitEintragController {

    BenutzerRepository benutzerRepository;

    @Autowired
    public KryptonitEintragController(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }

    @PostMapping("/kryptonit/{kryptonitId}/eintrag")
    public Kryptonit neuenEintragErstellen(@PathVariable Long kryptonitId, @RequestBody kryptonitEintragRequestDTO kryptonitEintragRequestDTO, @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));

        List<Kryptonit> kryponiteDesBenutzers = eingeloggterBenutzer.getKryptonite();

        Optional<Kryptonit> veraendertesKryptonitOptional = kryponiteDesBenutzers.stream()
                .filter(kryptonit -> kryptonit.getKryptonitId().equals(kryptonitId))
                .findFirst();

        if (veraendertesKryptonitOptional.isPresent()) {
            Kryptonit veraendertesKryptonit = veraendertesKryptonitOptional.get();
            veraendertesKryptonit.getTaeglicheEintraege().add(new KryptonitEintrag(kryptonitEintragRequestDTO.haeufigkeit(), veraendertesKryptonit));
            benutzerRepository.save(eingeloggterBenutzer);
            return veraendertesKryptonit;
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Benutzer hat dieses Kryptonit nicht.");
    }
}
