package com.example.aw_finalprojectbackend.stimmung;

import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.example.aw_finalprojectbackend.benutzer.BenutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StimmungService {

    private final BenutzerRepository benutzerRepository;

    @Autowired
    public StimmungService(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }

    public List<Stimmung> erhalteAlleStimmungen(Benutzer benutzer) {
        if (benutzer != null) {
            return benutzer.getStimmungen();
        } else {
            return Collections.emptyList();
        }
    }

    public Stimmung erstelleStimmung(Benutzer benutzer, StimmungRequestDTO stimmungRequestDTO) {
        Stimmung stimmung = new Stimmung(benutzer, stimmungRequestDTO.rating(), stimmungRequestDTO.kommentar());
        benutzer.getStimmungen().add(stimmung);
        benutzerRepository.save(benutzer);
        return stimmung;
    }

    public Stimmung editiereStimmung(Benutzer benutzer, Long stimmungId, StimmungRequestDTO stimmungRequestDTO) {
        for (Stimmung stimmung : benutzer.getStimmungen()) {
            if (stimmung.getStimmungId().equals(stimmungId)) {
                stimmung.setRating(stimmungRequestDTO.rating());
                stimmung.setKommentar(stimmungRequestDTO.kommentar());
                benutzerRepository.save(benutzer);
                return stimmung;
            }
        }
        Stimmung neueStimmung = new Stimmung(benutzer, stimmungRequestDTO.rating(), stimmungRequestDTO.kommentar());
        benutzer.getStimmungen().add(neueStimmung);
        benutzerRepository.save(benutzer);
        return neueStimmung;
    }


    public boolean loescheStimmung(Long stimmungId, Benutzer benutzer) {
        if (benutzer != null) {
            benutzer.getStimmungen().removeIf(s -> s.getStimmungId().equals(stimmungId));
            benutzerRepository.save(benutzer);
            return true;
        }
        return false;
    }
}
