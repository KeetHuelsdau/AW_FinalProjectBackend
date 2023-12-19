package com.example.aw_finalprojectbackend.benutzer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Benutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long benutzerId;

    @Column(unique = true)
    private String benutzerName;

    @JsonIgnore
    private String passwort;

    String vorname;
    String nachname;
    int alter;
    String geschlecht;

    @OneToMany(mappedBy = "Stimmung", cascade = CascadeType.ALL, orphanRemoval = true)
    List <Stimmung> stimmungen;
    @OneToMany(mappedBy = "Kryptonit", cascade = CascadeType.ALL, orphanRemoval = true)
    List <Kryptonit> kryptonite;

    public Benutzer() {
    }

    public Benutzer(String benutzerName, String passwort, String vorname, String nachname, int alter, String geschlecht) {
        this.benutzerName = benutzerName;
        this.passwort = passwort;
        this.vorname = vorname;
        this.nachname = nachname;
        this.alter = alter;
        this.geschlecht = geschlecht;
    }
}
