package com.example.aw_finalprojectbackend.stimmung;

public enum StimmungRating {
    NULL(0,"Alles total Mist!"),
    EINS(1,"Weiß gerade nicht weiter..."),
    ZWEI(2,"Irgendwie blöd..."),
    DREI(3,"Normal"),
    VIER(4,"Läuft!"),
    FUNF(5,"Einfach gut druff!"),
    SECHS(6,"Glücklich!");

    private final int wert;
    private final String beschreibung;

    StimmungRating(int wert, String beschreibung) {
        this.wert = wert;
        this.beschreibung = beschreibung;
    }

    public int getWert() {
        return wert;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public static StimmungRating valueOf(int wert) {
        for (StimmungRating rating : values()){
            if(rating.wert == wert){
                return rating;
            }
        }
        throw new IllegalArgumentException("Ungültiges wert"+ wert);
    }
}
