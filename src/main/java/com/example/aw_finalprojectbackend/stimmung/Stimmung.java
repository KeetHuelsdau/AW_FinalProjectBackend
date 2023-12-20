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

    private int rating;

    private LocalDateTime erstellungszeit;

    @Size(min = 5,max = 255)
    private String kommentar;

    @ManyToOne
    @JsonIgnore
    private Benutzer benutzer;

    public Stimmung(Long id, int rating, LocalDateTime erstellungszeit, String kommentar) {
    public Stimmung(Benutzer benutzer,Long id, int rating, LocalDateTime erstellungszeit, String kommentar) {
        this.benutzer = benutzer;
        this.stimmungId = id;
        this.rating = rating;
        this.erstellungszeit = erstellungszeit;
        this.kommentar = kommentar;
    }

    public Stimmung() {
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
