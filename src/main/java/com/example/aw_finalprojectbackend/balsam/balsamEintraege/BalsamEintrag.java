package com.example.aw_finalprojectbackend.balsam.balsamEintraege;

import com.example.aw_finalprojectbackend.balsam.Balsam;
import com.example.aw_finalprojectbackend.kryponit.Kryptonit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class BalsamEintrag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long balsamEintragId;

    LocalDateTime zeitpunkt;

    private Boolean aktiv;

    @ManyToOne
    @JsonIgnore
    Balsam balsam;

    public BalsamEintrag() {}

    public BalsamEintrag(Balsam balsam) {
        this.zeitpunkt = LocalDateTime.now();;
        this.aktiv = true;
        this.balsam = balsam;
    }

    public Boolean getAktiv() {
        return aktiv;
    }

    public void setAktiv(Boolean aktiv) {
        this.aktiv = aktiv;
    }

    public void toggleAktiv() {
        this.aktiv = !this.aktiv;
    }

    public LocalDateTime getZeitpunkt() {
        return zeitpunkt;
    }

    public String getErstellungszeitalsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy");
        return zeitpunkt.format(formatter);
    }
}
