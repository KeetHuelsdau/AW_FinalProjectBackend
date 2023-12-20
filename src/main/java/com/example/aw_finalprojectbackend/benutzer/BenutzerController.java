package com.example.aw_finalprojectbackend.benutzer;

import com.example.aw_finalprojectbackend.sitzung.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.time.Instant;
import java.util.Optional;

@RestController
public class BenutzerController {
    private final BenutzerRepository benutzerRepository;
    private final SitzungRepository sitzungRepository;

    @Autowired
    public BenutzerController(BenutzerRepository benutzerRepository, SitzungRepository sitzungRepository) {
        this.benutzerRepository = benutzerRepository;
        this.sitzungRepository = sitzungRepository;
    }

    @PostMapping("/einloggen")
    public LoginResponseDTO login(@CookieValue(value = "sitzungsId", defaultValue = "") String sitzungsId, @RequestBody LoginRequestDTO login, HttpServletResponse response) {
        Optional<Benutzer> benutzerOptional = benutzerRepository.findByBenutzerNameAndPasswort(login.benutzerName(), login.passwort());

        if (benutzerOptional.isPresent()) {

            Optional<Sitzung> sitzungOptional = sitzungRepository.findBySitzungsIdAndAktivBisAfter(sitzungsId, Instant.now());
            if (sitzungOptional.isEmpty()){

            Sitzung sitzung = new Sitzung(benutzerOptional.get(), Instant.now().plusSeconds(3*60*60));
            sitzungRepository.save(sitzung);

            Cookie cookie = new Cookie("sitzungsId", sitzung.getSitzungsId());
            response.addCookie(cookie);

            return new LoginResponseDTO("Erfolgreich eingeloggt",login.benutzerName());
            } throw new ResponseStatusException(HttpStatus.CONFLICT, "Benutzer ist bereits eingeloggt!");
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Benutzer oder Passwort sind nicht korrekt!");
    }

    @PostMapping("/ausloggen")
    public LogoutResponseDTO logout(@CookieValue(value = "sitzungsId", defaultValue = "") String sitzungsId, HttpServletResponse response) {
        Optional<Sitzung> sitzungOptional = sitzungRepository.findBySitzungsIdAndAktivBisAfter(sitzungsId, Instant.now());

        if(sitzungOptional.isPresent()){
            sitzungRepository.delete(sitzungOptional.get());
            Cookie cookie = new Cookie("sitzungsId", "");
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            return new LogoutResponseDTO("Erfolgreich ausgeloggt!");
        } throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kein Benutzer eingeloggt!");
    }

    @PostMapping("/register")
    public RegisterResponseDTO createUser(@RequestBody RegisterRequestDTO registerRequestDTO){
        Optional<Benutzer> duplicateOptional = benutzerRepository.findByBenutzerName(registerRequestDTO.benutzerName());

        if(duplicateOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Benutzername ist bereits vergeben!");
        }
        else {
            if (registerRequestDTO.passwort1().equals(registerRequestDTO.passwort2())){
                Benutzer erstelleBenutzer = new Benutzer(registerRequestDTO.benutzerName(),registerRequestDTO.passwort1(), registerRequestDTO.vorname(),registerRequestDTO.nachname(), registerRequestDTO.geschlecht());
                benutzerRepository.save(erstelleBenutzer);
                return new RegisterResponseDTO(registerRequestDTO.benutzerName());
            }
            else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwörter stimmen nicht überein.");
        }
    }
}
