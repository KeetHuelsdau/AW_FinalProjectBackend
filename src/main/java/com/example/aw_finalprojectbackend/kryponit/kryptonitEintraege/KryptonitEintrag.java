package com.example.aw_finalprojectbackend.kryponit.kryptonitEintraege;

import com.example.aw_finalprojectbackend.kryponit.Kryptonit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class KryptonitEintrag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long kryptonitEintragId;

    @Column(unique = true)
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

    public int getHaeufigkeit() {
        return haeufigkeit;
    }
}
