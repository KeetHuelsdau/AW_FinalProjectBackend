package com.example.aw_finalprojectbackend.kryponit;

import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.example.aw_finalprojectbackend.kryponit.kryptonitEintraege.KryptonitEintrag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Kryptonit {
    @Id
    @GeneratedValue
    private Long kryptonitId;
    private String bezeichnung;

    @ManyToOne
    @JsonIgnore
    private Benutzer benutzer;

    @OneToMany(mappedBy = "kryptonit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KryptonitEintrag> taeglicheEintraege;

    public Kryptonit() {
    }

    public Kryptonit(String bezeichnung, Benutzer benutzer) {
        this.bezeichnung = bezeichnung;
        this.benutzer = benutzer;
        this.taeglicheEintraege = new ArrayList<>();
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

    public List<KryptonitEintrag> getTaeglicheEintraege() {
        return taeglicheEintraege;
    }
}
