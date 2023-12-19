package com.example.aw_finalprojectbackend.benutzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class BenutzerController {
    private final BenutzerRepository benutzerRepository;

    @Autowired
    public BenutzerController(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }

    @PostMapping("/register")
    public RegisterResponseDTO createUser(@RequestBody RegisterRequestDTO registerRequestDTO){
        Optional<Benutzer> duplicateOptional = benutzerRepository.findByBenutzerName(registerRequestDTO.benutzerName());

        if(duplicateOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Benutzername ist bereits vergeben!");
        }
        else {
            if (registerRequestDTO.passwort1().equals(registerRequestDTO.passwort2())){
                Benutzer erstelleBenutzer = new Benutzer(registerRequestDTO.benutzerName(),registerRequestDTO.passwort1(), registerRequestDTO.vorname(),registerRequestDTO.nachname(), registerRequestDTO.alter(), registerRequestDTO.geschlecht());
                benutzerRepository.save(erstelleBenutzer);
                return new RegisterResponseDTO(registerRequestDTO.benutzerName());
            }
            else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwörter stimmen nicht überein.");
        }
    }
}
