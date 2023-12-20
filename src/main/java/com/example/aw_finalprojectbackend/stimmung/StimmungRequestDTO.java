package com.example.aw_finalprojectbackend.stimmung;

public class StimmungRequestDTO {
    Long stimmungId;
    int rating;
    String kommentar;

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

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }
}
