package com.example.aw_finalprojectbackend.stimmung;

import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Stimmung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stimmungId;

    private String stimmungName;

    private int rating;

    private LocalDateTime erstellungszeit;

    @Size(min = 5,max = 255)
    private String kommentar;

    @ManyToOne
    @JsonIgnore
    private Benutzer benutzer;

    public Stimmung(Benutzer benutzer, int rating, String kommentar) {
        this.benutzer = benutzer;
        this.rating = rating;
        this.erstellungszeit = LocalDateTime.now();
        this.kommentar = kommentar;
    }

    public Stimmung() {
    }



    public String getStimmungName() {
        return stimmungName;
    }

    public void setStimmungName(String stimmungName) {
        this.stimmungName = stimmungName;
    }

    public Benutzer getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
    }

    public Long getStimmungId() {
        return stimmungId;
    }

    public void setStimmungId(Long stimmungId) {
        this.stimmungId = stimmungId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getErstellungszeit() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm");
        return erstellungszeit.format(formatter);
    }

    public void setErstellungszeit(LocalDateTime erstellungszeit) {
        this.erstellungszeit = erstellungszeit;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }
}
