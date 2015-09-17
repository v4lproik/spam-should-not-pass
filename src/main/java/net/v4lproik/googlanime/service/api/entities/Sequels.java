package net.v4lproik.googlanime.service.api.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Sequels")
public class Sequels implements Serializable{

    @Id
    private Long idAnime;

    @Id
    private Long idSequel;

    public Sequels(Long idAnime, Long idSequel) {
        this.idAnime = idAnime;
        this.idSequel = idSequel;
    }

    public Long getIdAnime() {
        return idAnime;
    }

    public void setIdAnime(Long idAnime) {
        this.idAnime = idAnime;
    }

    public Long getIdSequel() {
        return idSequel;
    }

    public void setIdSequel(Long idSequel) {
        this.idSequel = idSequel;
    }
}
