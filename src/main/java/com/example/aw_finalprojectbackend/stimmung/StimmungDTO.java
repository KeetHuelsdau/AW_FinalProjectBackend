package com.example.aw_finalprojectbackend.stimmung;

public class StimmungDTO {
    int rating;
    String kommentar;

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
