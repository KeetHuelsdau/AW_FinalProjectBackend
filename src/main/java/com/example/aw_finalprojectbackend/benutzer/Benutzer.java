package com.example.aw_finalprojectbackend.benutzer;

import com.example.aw_finalprojectbackend.kryponit.Kryptonit;
import com.example.aw_finalprojectbackend.stimmung.Stimmung;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "benutzer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Stimmung> stimmungen;
    @OneToMany(mappedBy = "benutzer", cascade = CascadeType.ALL, orphanRemoval = true)
    List <Kryptonit> kryptonite;

    public Benutzer() {
    }

    public Benutzer(String benutzerName, String passwort, String vorname, String nachname, String geschlecht) {
        this.benutzerName = benutzerName;
        this.passwort = passwort;
        this.vorname = vorname;
        this.nachname = nachname;
        this.geschlecht = geschlecht;
        this.stimmungen = new ArrayList<>();
        this.kryptonite = new ArrayList<>();
    }

    public long getBenutzerId() {
        return benutzerId;
    }

    public void setBenutzerId(long benutzerId) {
        this.benutzerId = benutzerId;
    }

    public String getBenutzerName() {
        return benutzerName;
    }

    public void setBenutzerName(String benutzerName) {
        this.benutzerName = benutzerName;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(String geschlecht) {
        this.geschlecht = geschlecht;
    }

    public List<Stimmung> getStimmungen() {
        return stimmungen;
    }

    public void setStimmungen(List<Stimmung> stimmungen) {
        this.stimmungen = stimmungen;
    }
    public List<Kryptonit> getKryptonite() {
        return kryptonite;
    }

    public void setKryptonite(List<Kryptonit> kryptonite) {
        this.kryptonite = kryptonite;
    }
}
