package com.example.aw_finalprojectbackend.sitzung;

import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.example.aw_finalprojectbackend.benutzer.BenutzerRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

@RestController
public class SitzungController {

    private SitzungRepository sitzungRepository;
    private BenutzerRepository benutzerRepository;

    @PostMapping("/einloggen")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO login, HttpServletResponse response) {
        Optional<Benutzer> benutzerOptional = benutzerRepository.findByBenutzerNameAndPasswort(login.benutzerName(), login.passwort());

        if (benutzerOptional.isPresent()) {

            Sitzung sitzung = new Sitzung(benutzerOptional.get(), Instant.now().plusSeconds(3*60*60));
            sitzungRepository.save(sitzung);

            Cookie cookie = new Cookie("sitzungsId", sitzung.getSitzungsId());
            response.addCookie(cookie);

            return new LoginResponseDTO("Erfolgreich eingeloggt",login.benutzerName());
        }

        // When login-does not work
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Benutzername oder Passwort ist nicht korrekt.");
    }

    @PostMapping("/ausloggen")
    public LogoutResponseDTO logout(@CookieValue(value = "sitzungsId", defaultValue = "") String sitzungsId, HttpServletResponse response) {
        Optional<Sitzung> sitzungOptional = sitzungRepository.findByIdAndAktivBisAfter(sitzungsId, Instant.now());
        // Delete session in database
        sitzungOptional.ifPresent(sitzung -> sitzungRepository.delete(sitzung));

        Cookie cookie = new Cookie("sitzungsId", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return new LogoutResponseDTO("Erfolgreich ausgeloggt!");
    }
}
