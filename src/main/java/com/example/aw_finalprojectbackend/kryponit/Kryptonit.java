package com.example.aw_finalprojectbackend.kryponit;

import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import jakarta.persistence.*;
import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Kryptonit {
    @Id
    @GeneratedValue
    private Long kryptonitId;
    private String bezeichnung;
    private int haeufigkeit;

    @ManyToOne
    @JsonIgnore
    private Benutzer benutzer;

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
}
