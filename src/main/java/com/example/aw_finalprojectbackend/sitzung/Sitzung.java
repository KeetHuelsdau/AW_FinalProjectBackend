package com.example.aw_finalprojectbackend.sitzung;

import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.Instant;
import java.util.UUID;

@Entity
public class Sitzung {
    @Id
    private String sitzungsId = UUID.randomUUID().toString();

    @ManyToOne
    private Benutzer benutzer;

    private Instant aktivBis;

    public Sitzung() {
    }

    public Sitzung(Benutzer benutzer, Instant aktivBis) {
        this.benutzer = benutzer;
        this.aktivBis = aktivBis;
    }

    public Benutzer getBenutzer() {
        return benutzer;
    }

    public String getSitzungsId() {
        return sitzungsId;
    }

    public void setAktivBis(Instant aktivBis) {
        this.aktivBis = aktivBis;
    }
}
