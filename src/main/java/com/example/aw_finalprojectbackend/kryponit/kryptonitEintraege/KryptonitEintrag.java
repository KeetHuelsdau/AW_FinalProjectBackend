package com.example.aw_finalprojectbackend.kryponit.kryptonitEintraege;

import com.example.aw_finalprojectbackend.kryponit.Kryptonit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class KryptonitEintrag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long kryptonitEintragId;

    LocalDateTime zeitpunkt;

    int haeufigkeit;

    @ManyToOne
    @JsonIgnore
    Kryptonit kryptonit;

    public KryptonitEintrag() {}

    public KryptonitEintrag(int haeufigkeit, Kryptonit kryptonit) {
        this.zeitpunkt = LocalDateTime.now();
        this.haeufigkeit = haeufigkeit;
        this.kryptonit = kryptonit;
    }

    public LocalDateTime getZeitpunkt() {
        return zeitpunkt;
    }

    public String getErstellungszeitalsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy");
        return zeitpunkt.format(formatter);
    }

    public int getHaeufigkeit() {
        return haeufigkeit;
    }

    public void setHaeufigkeit(int haeufigkeit) {
        this.haeufigkeit = haeufigkeit;
    }
}
