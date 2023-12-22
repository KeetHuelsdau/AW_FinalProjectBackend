package com.example.aw_finalprojectbackend.stimmung;

import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.example.aw_finalprojectbackend.benutzer.BenutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StimmungService {

    private final BenutzerRepository benutzerRepository;

    @Autowired
    public StimmungService(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }


    public Stimmung erstelleStimmung(Benutzer benutzer, StimmungRequestDTO stimmungRequestDTO) {
        Stimmung stimmung = new Stimmung(benutzer, stimmungRequestDTO.rating(), stimmungRequestDTO.kommentar());
        benutzer.getStimmungen().add(stimmung);
        benutzerRepository.save(benutzer);
        return stimmung;
    }

    public Stimmung editiereStimmung(Benutzer benutzer, Long stimmungId, StimmungRequestDTO stimmungRequestDTO) {
        Optional<Stimmung> bearbeiteteStimmungOptional = benutzer.getStimmungen().stream()
                .filter(stimmung -> stimmung.getStimmungId().equals(stimmungId))
                .findFirst();

        if (bearbeiteteStimmungOptional.isPresent()) {
            Stimmung bearbeiteteStimmung = bearbeiteteStimmungOptional.get();
            bearbeiteteStimmung.setRating(stimmungRequestDTO.rating());
            bearbeiteteStimmung.setKommentar(stimmungRequestDTO.kommentar());
            benutzerRepository.save(benutzer);
            return bearbeiteteStimmung;
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
