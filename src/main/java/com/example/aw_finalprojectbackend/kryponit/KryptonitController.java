package com.example.aw_finalprojectbackend.kryponit;

import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class KryptonitController {
    @GetMapping("/kryptonite")
    public List<Kryptonit> erhalteAlleKryptonite(@ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional){
        Benutzer eingeloggterBenutzer= eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));

        //return kryponitRepository.findAll();

        //TODO BenutzerRepository...
        return null;
    }

    @PostMapping("/kryptonit")
    public Kryptonit erstelleKryptonit(@RequestBody Kryptonit kryptonit, @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));

        //        return kryponitRepository.save(kryptonit);

        //TODO BenutzerRepository...
        return null;
    }

    @DeleteMapping("/kryptonit/{kryptonitId}")
    public void loescheKryptonit(@PathVariable Long kryptonitId, @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        Benutzer eingeloggterBenutzer= eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));

        //kryponitRepository.deleteById(kryptonitId);
        //TODO BenutzerRepository...
    }

    @PutMapping("/kryptonit/{kryptonitId}")
    public Kryptonit aktualisiereKryptonitWert(@PathVariable Long id, @RequestParam int neuerKryptonitWert, @ModelAttribute("eingeloggterBenutzer") Optional<Benutzer> eingeloggterBenutzerOptional) {
        Benutzer eingeloggterBenutzer = eingeloggterBenutzerOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login erforderlich"));

        return null;

    }

}
