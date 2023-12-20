package com.example.aw_finalprojectbackend.stimmung;

public class StimmungResponseDTO {
    int rating;
    String erstellungszeit;
    String kommentar;

    public StimmungResponseDTO() {
    }

    public String getErstellungszeit() {
        return erstellungszeit;
    }

    public void setErstellungszeit(String erstellungszeit) {
        this.erstellungszeit = erstellungszeit;
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
