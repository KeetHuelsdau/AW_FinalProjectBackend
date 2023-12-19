package com.example.aw_finalprojectbackend.sitzung;

import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.example.aw_finalprojectbackend.benutzer.BenutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.Instant;
import java.util.Optional;

@ControllerAdvice
public class SitzungControllerAdvice {
    private final SitzungRepository sitzungRepository;
    private final BenutzerRepository benutzerRepository;
    private final String standardBenutzerName;


    @Autowired
    public SitzungControllerAdvice(SitzungRepository sitzungRepository, BenutzerRepository benutzerRepository, Environment env) {
        this.sitzungRepository = sitzungRepository;
        this.benutzerRepository = benutzerRepository;
        this.standardBenutzerName = env.getProperty("login.default");
    }

    @ModelAttribute("eingeloggterBenutzer")
    public Optional<Benutzer> eingeloggterBenutzer(@CookieValue(name = "sitzungsId", defaultValue = "") String sitzungsId) {
        if(standardBenutzerName != null && !standardBenutzerName.isEmpty()){
            return benutzerRepository.findByBenutzer(standardBenutzerName);
        }

        if (!sitzungsId.isEmpty()) {
            Optional<Sitzung> sitzungOptional = sitzungRepository.findByIdAndAktivBisAfter(sitzungsId, Instant.now());

            return sitzungOptional.map(sitzung -> {
                // new expiresAt value for the current session
                sitzung.setAktivBis(Instant.now().plusSeconds(7 * 24 * 60 * 60));
                sitzungRepository.save(sitzung);

                return sitzung.getBenutzer();
            });
        }
        return Optional.empty();
    }
}
