package com.example.aw_finalprojectbackend.kryponit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Kryptonit {
    @Id
    @GeneratedValue
    private Long kryptonitId;
    private String bezeichnung;

    private int haeufigkeit;


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
