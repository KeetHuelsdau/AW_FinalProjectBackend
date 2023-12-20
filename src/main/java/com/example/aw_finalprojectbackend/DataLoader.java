package com.example.aw_finalprojectbackend;


import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.example.aw_finalprojectbackend.benutzer.BenutzerRepository;
import com.example.aw_finalprojectbackend.kryponit.Kryptonit;
import com.example.aw_finalprojectbackend.kryponit.KryptonitRequestDTO;
import com.example.aw_finalprojectbackend.stimmung.Stimmung;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements ApplicationRunner {

    private BenutzerRepository benutzerRepository;

    @Autowired
    public DataLoader (BenutzerRepository benutzerRepository){
        this.benutzerRepository = benutzerRepository;
    }

    public  void run(ApplicationArguments args) {

        Benutzer moritzrose = new Benutzer("moritzrose", "moritzrose", "Moritz", "Rose", "männlich");
        Kryptonit schokolade = new Kryptonit("Schokolade", moritzrose);
        Kryptonit kaffee = new Kryptonit("Kaffee", moritzrose);
        moritzrose.getKryptonite().add(schokolade);
        moritzrose.getKryptonite().add(kaffee);
        moritzrose.getKryptonite().add(new Kryptonit("Zocken", moritzrose));

        Stimmung gluecklich = new Stimmung("Glücklich", moritzrose, 5, LocalDateTime.now(), "Glücklich!");
        moritzrose.getStimmungen().add(gluecklich);
        moritzrose.getStimmungen().add(new Stimmung("Traurig", moritzrose, 3, LocalDateTime.now(), "Traurig!"));

        benutzerRepository.save(moritzrose);


        Benutzer igorgrab = new Benutzer("igorgrab", "igorgrab", "Igor", "Grab", "männlich");
        Kryptonit surfen = new Kryptonit("Surfen", igorgrab);
        Kryptonit koffein = new Kryptonit("Kaffee", igorgrab);
        Kryptonit gaming = new Kryptonit("Gaming", igorgrab);
        igorgrab.getKryptonite().add(surfen);
        igorgrab.getKryptonite().add(koffein);
        igorgrab.getKryptonite().add(gaming);

        igorgrab.getStimmungen().add(new Stimmung());
        benutzerRepository.save(igorgrab);


        Benutzer keethuelsdau = new Benutzer("keethuelsdau", "keethuelsdau", "Keet", "Huelsdau", "weiblich");
        Kryptonit kekse = new Kryptonit("Kekse", keethuelsdau);
        Kryptonit haribo = new Kryptonit("Haribo", keethuelsdau);
        Kryptonit prokrastination = new Kryptonit("Prokrastination", keethuelsdau);
        keethuelsdau.getKryptonite().add(kekse);
        keethuelsdau.getKryptonite().add(haribo);
        keethuelsdau.getKryptonite().add(prokrastination);

        keethuelsdau.getStimmungen().add(new Stimmung());
        benutzerRepository.save(keethuelsdau);



    }
}

