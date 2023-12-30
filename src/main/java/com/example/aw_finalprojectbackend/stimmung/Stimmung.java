package com.example.aw_finalprojectbackend.stimmung;

import com.example.aw_finalprojectbackend.benutzer.Benutzer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Stimmung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stimmungId;


    @Min(0)
    @Max(6)
    private int rating;

    private LocalDateTime erstellungszeit;

    @Size(min = 0, max = 255)
    private String kommentar;

    @ManyToOne
    @JsonIgnore
    private Benutzer benutzer;

    public Stimmung(Benutzer benutzer, int rating, String kommentar) {
        this.benutzer = benutzer;
        this.setRating(rating);
        this.erstellungszeit = LocalDateTime.now();
        this.kommentar = kommentar;
    }

    public Stimmung() {
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
        if (rating < StimmungRating.NULL.getWert() || rating > StimmungRating.SECHS.getWert()) {
            throw new IllegalArgumentException("Ungültiges Rating: " + rating);
        }
        this.rating = rating;
    }

    public LocalDateTime getErstellungszeit() {
        return erstellungszeit;
    }

    public String getErstellungszeitalsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm");
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
