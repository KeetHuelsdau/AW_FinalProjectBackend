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
    private BenutzerRepository benutzerRepository;

    @Autowired
    public BenutzerController(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }

    @PostMapping("/register")
    public RegisterResponseDTO createUser(@RequestBody RegisterRequestDTO registerRequestDTO){
        Optional<User> duplicateOptional = userRepository.findByUsername(registerRequestDTO.getUsername());

        if(duplicateOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken!");
        }
        else {
            if (registerRequestDTO.getPassword1().equals(registerRequestDTO.getPassword2())){
                User createUser = new User(registerRequestDTO.getUsername(),registerRequestDTO.getPassword1());
                userRepository.save(createUser);
                return new RegisterResponseDTO(registerRequestDTO.getUsername());
            }
            else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords are not the same!");
        }
    }
}
