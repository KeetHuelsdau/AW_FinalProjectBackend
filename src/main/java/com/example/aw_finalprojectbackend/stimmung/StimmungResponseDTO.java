package com.example.aw_finalprojectbackend.stimmung;

public class StimmungResponseDTO{
    int rating;
    String stimmungName;
    String erstellungszeit;
    String kommentar;

    public StimmungResponseDTO(int rating, String stimmungName, String erstellungszeit, String kommentar) {
        this.rating = rating;
        this.stimmungName = stimmungName;
        this.erstellungszeit = erstellungszeit;
        this.kommentar = kommentar;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getStimmungName() {
        return stimmungName;
    }

    public void setStimmungName(String stimmungName) {
        this.stimmungName = stimmungName;
    }

    public String getErstellungszeit() {
        return erstellungszeit;
    }

    public void setErstellungszeit(String erstellungszeit) {
        this.erstellungszeit = erstellungszeit;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }
}
