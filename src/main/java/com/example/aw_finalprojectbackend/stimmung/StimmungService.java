package com.example.aw_finalprojectbackend.stimmung;

import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.example.aw_finalprojectbackend.benutzer.BenutzerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class StimmungService{

    @Autowired
    private BenutzerRepository benutzerRepository;
    private StimmungRepository stimmungRepository;

    public Optional<Benutzer> getStimmungen(String username) {
        return benutzerRepository.findByBenutzerName(username);
    }

   /* public Stimmung createStimmung(StimmungResponseDTO stimmungDTO) {
        Stimmung stimmung = new Stimmung();
        stimmung.setRating(stimmungDTO.getRating());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime erstellungszeit = LocalDateTime.parse(stimmungDTO.getErstellngszeit(), formatter);
        stimmung.setErstellungszeit(erstellungszeit);

        return benutzerRepository.save(stimmung);
    }*/

    public Stimmung createStimmung(StimmungResponseDTO stimmungDTO) {
        Stimmung stimmung = new Stimmung();

        stimmung.setRating(stimmungDTO.getRating());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime erstellungszeit = LocalDateTime.parse(stimmungDTO.getErstellungszeit(), formatter);
        stimmung.setErstellungszeit(erstellungszeit);
        stimmung.setKommentar(stimmungDTO.getKommentar());

        return stimmungRepository.save(stimmung);
    }



    /* public Stimmung editStimmung(String username, StimmungResponseDTO stimmungDTO) {
        Benutzer existingBenutzer = benutzerRepository.findByBenutzerName(username)
                .orElseThrow(() -> new EntityNotFoundException("Stimmung mit name " + username + " nicht gefunden"));

        Stimmung existingStimmung = existingBenutzer.getStimmung();

        existingStimmung.setRating(stimmungDTO.getRating());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime erstellungszeit = LocalDateTime.parse(stimmungDTO.getErstellngszeit(), formatter);
        existingStimmung.setErstellungszeit(erstellungszeit);
        existingStimmung.setKommentar(stimmungDTO.getKommentar());

        benutzerRepository.save(existingBenutzer);

        return existingStimmung;
    }*/
   public Stimmung editStimmung(Long stimmungId, StimmungResponseDTO stimmungDTO) {
       Stimmung existingStimmung = stimmungRepository.findById(stimmungId)
               .orElseThrow(() -> new EntityNotFoundException("Stimmung mit id " + stimmungId + " nicht gefunden"));

       existingStimmung.setRating(stimmungDTO.getRating());
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
       LocalDateTime erstellungszeit = LocalDateTime.parse(stimmungDTO.getErstellungszeit(), formatter);
       existingStimmung.setErstellungszeit(erstellungszeit);
       existingStimmung.setKommentar(stimmungDTO.getKommentar());

       return stimmungRepository.save(existingStimmung);
   }



    public void deleteStimmung(Long stimmungId) {
       Stimmung existingStimmung = stimmungRepository.findById(stimmungId)
                .orElseThrow(() -> new EntityNotFoundException("Stimmung mit id " + stimmungId + " nicht gefunden"));
        existingStimmung.setStimmungId(null);

        stimmungRepository.save(existingStimmung);
    }

}
