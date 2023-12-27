/*
package com.example.aw_finalprojectbackend;
import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.example.aw_finalprojectbackend.benutzer.BenutzerRepository;
import com.example.aw_finalprojectbackend.kryponit.Kryptonit;
import com.example.aw_finalprojectbackend.kryponit.KryptonitRequestDTO;
import com.example.aw_finalprojectbackend.kryponit.kryptonitEintraege.KryptonitEintrag;
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


        moritzrose.getStimmungen().add(new Stimmung(moritzrose, 3, "Traurig!"));

        benutzerRepository.save(moritzrose);


        Benutzer igorgrab = new Benutzer("igorgrab", "igorgrab", "Igor", "Grab", "männlich");

        igorgrab.getKryptonite().add(new Kryptonit("Surfen", igorgrab));
        igorgrab.getKryptonite().add(new Kryptonit("Kaffee", igorgrab));
        igorgrab.getKryptonite().add(new Kryptonit("Gaming", igorgrab));

        igorgrab.getStimmungen().add(new Stimmung(igorgrab, 3, "Alles ok!"));
        benutzerRepository.save(igorgrab);


        Benutzer keethuelsdau = new Benutzer("keethuelsdau", "keethuelsdau", "Keet", "Huelsdau", "weiblich");
        Kryptonit kekse = new Kryptonit("Kekse", keethuelsdau);
        Kryptonit haribo = new Kryptonit("Haribo", keethuelsdau);
        Kryptonit prokrastination = new Kryptonit("Prokrastination", keethuelsdau);
        keethuelsdau.getKryptonite().add(kekse);
        keethuelsdau.getKryptonite().add(haribo);
        keethuelsdau.getKryptonite().add(prokrastination);

        keethuelsdau.getStimmungen().add(new Stimmung(keethuelsdau, 4,"Solala!"));
        benutzerRepository.save(keethuelsdau);

    }
}
    */

