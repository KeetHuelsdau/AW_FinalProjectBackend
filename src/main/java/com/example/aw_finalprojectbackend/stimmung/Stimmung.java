package com.example.aw_finalprojectbackend.stimmung;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    public Stimmung(Long id, int rating, LocalDateTime erstellungszeit, String kommentar) {
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
