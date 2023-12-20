package com.example.aw_finalprojectbackend.benutzer;

import com.example.aw_finalprojectbackend.kryponit.Kryptonit;
import com.example.aw_finalprojectbackend.stimmung.Stimmung;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

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
    String geschlecht;

    @OneToMany(mappedBy = "benutzer", cascade = CascadeType.ALL)
    List<Stimmung> stimmungen;
    @OneToMany(mappedBy = "benutzer", cascade = CascadeType.ALL)
    List <Kryptonit> kryptonite;

    public Benutzer() {
    }

    public Benutzer(String benutzerName, String passwort, String vorname, String nachname, String geschlecht) {
        this.benutzerName = benutzerName;
        this.passwort = passwort;
        this.vorname = vorname;
        this.nachname = nachname;
        this.geschlecht = geschlecht;
    }

    public List<Kryptonit> getKryptonite() {
        return kryptonite;
    }

    public void setKryptonite(List<Kryptonit> kryptonite) {
        this.kryptonite = kryptonite;
    }
}
