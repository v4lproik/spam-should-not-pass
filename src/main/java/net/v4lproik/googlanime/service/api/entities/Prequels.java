package net.v4lproik.googlanime.service.api.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Prequels")
public class Prequels implements Serializable{

    @Id
    private Long idAnime;

    @Id
    private Long idPrequel;

    public Prequels(Long idAnime, Long idPrequel) {
        this.idAnime = idAnime;
        this.idPrequel = idPrequel;
    }

    public Long getIdAnime() {
        return idAnime;
    }

    public void setIdAnime(Long idAnime) {
        this.idAnime = idAnime;
    }

    public Long getIdPrequel() {
        return idPrequel;
    }

    public void setIdPrequel(Long idPrequel) {
        this.idPrequel = idPrequel;
    }
}
