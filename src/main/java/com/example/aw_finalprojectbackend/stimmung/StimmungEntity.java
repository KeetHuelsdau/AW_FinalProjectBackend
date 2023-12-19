package com.example.aw_finalprojectbackend.stimmung;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
public class StimmungEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stimmungId;

    private int rating;

    private LocalDateTime erstellungszeit;

    @Size(min = 5,max = 255)
    private String kommentar;

    public StimmungEntity(Long id, int rating, LocalDateTime erstellungszeit, String kommentar) {
        this.stimmungId = id;
        this.rating = rating;
        this.erstellungszeit = erstellungszeit;
        this.kommentar = kommentar;
    }

    public StimmungEntity() {
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

    public LocalDateTime getErstellungszeit() {
        return erstellungszeit;
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
