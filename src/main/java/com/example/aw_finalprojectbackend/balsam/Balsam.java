package com.example.aw_finalprojectbackend.balsam;

import com.example.aw_finalprojectbackend.balsam.balsamEintraege.BalsamEintrag;
import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Balsam {

    @Id
    @GeneratedValue
    private Long balsamId;
    private String bezeichnung;


    @ManyToOne
    @JsonIgnore
    private Benutzer benutzer;

    @OneToMany(mappedBy = "balsam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BalsamEintrag> taeglicheEintraege;

    @Transient //diese Annotation verhindert, dass die Eigenschaft in der Datenbank gespeichert wird
    private String farbe;

    public Balsam() {
    }

    public Balsam(String bezeichnung, Benutzer benutzer) {
        this.bezeichnung = bezeichnung;
        this.benutzer = benutzer;
        this.taeglicheEintraege = new ArrayList<>();
    }

    public Long getBalsamId() {
        return balsamId;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public List<BalsamEintrag> getTaeglicheEintraege() {
        return taeglicheEintraege;
    }

    public String getFarbe() {
        return farbe;
    }

    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }
}
