package com.example.aw_finalprojectbackend.kryponit;

import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import jakarta.persistence.*;

@Entity
public class Kryptonit {
    @Id
    @GeneratedValue
    private Long kryptonitId;
    private String bezeichnung;
    private int haeufigkeit;

    @ManyToOne
    @JoinColumn(name = "benutzer_id")
    private Benutzer benutzer;

    public Kryptonit() {
    }
    public Kryptonit(Benutzer benutzer) {
        this.benutzer = benutzer;
    }
    public void setKryptonitId(Long kryptonitId) {
        this.kryptonitId = kryptonitId;
    }

    public Long getKryptonitId() {
        return kryptonitId;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public int getHaeufigkeit() {
        return haeufigkeit;
    }

    public void setHaeufigkeit(int haeufigkeit) {
        this.haeufigkeit = haeufigkeit;
    }

    public Benutzer getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
    }
}
