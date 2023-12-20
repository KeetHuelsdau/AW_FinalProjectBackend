package com.example.aw_finalprojectbackend.stimmung;

import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.example.aw_finalprojectbackend.benutzer.BenutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class StimmungService {

    private BenutzerRepository benutzerRepository;

    @Autowired
    public StimmungService(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }

    public List<Stimmung> getStimmungen(String username) {
        Optional<Benutzer> optionalBenutzer = benutzerRepository.findByBenutzerName(username);
        return optionalBenutzer.map(Benutzer::getStimmungen)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Keine Stimmungen eingefügt"));
    }

    public boolean createStimmung(String username, Stimmung newStimmung) {
        Optional<Benutzer> optionalBenutzer = benutzerRepository.findByBenutzerName(username);
        if (optionalBenutzer.isPresent()) {
            Benutzer benutzer = optionalBenutzer.get();
            newStimmung.setBenutzer(benutzer);
            benutzer.getStimmungen().add(newStimmung);
            benutzerRepository.save(benutzer);
            return true;
        }
        return false;
    }

    public boolean editStimmung(String username, Long stimmungId,Stimmung newStimmung) {
        Optional<Benutzer> optionalBenutzer = benutzerRepository.findByBenutzerName(username);
        if (optionalBenutzer.isPresent()) {
            Benutzer benutzer = optionalBenutzer.get();
            for (Stimmung stimmung : benutzer.getStimmungen()) {
                if (stimmung.getStimmungId().equals(stimmungId)) {
                    stimmung.setStimmungName(newStimmung.getStimmungName());
                    benutzerRepository.save(benutzer);
                    return true;
                }
            }
        }
        return false;
    }


    public boolean deleteStimmung(String username, Long stimmungId) {
        Optional<Benutzer> optionalBenutzer = benutzerRepository.findByBenutzerName(username);
        if (optionalBenutzer.isPresent()) {
            Benutzer benutzer = optionalBenutzer.get();
            benutzer.getStimmungen().removeIf(s -> s.getStimmungId().equals(stimmungId));
            benutzerRepository.save(benutzer);
            return true;
        }
        return false;
    }

}
