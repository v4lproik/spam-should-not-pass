package net.v4lproik.googlanime.service.api.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Adaptations")
public class Adaptations implements Serializable{

    @Id
    private Long idAnime;

    @Id
    private Long idAdaptation;

    public Adaptations(Long idAnime, Long idAdaptation) {
        this.idAnime = idAnime;
        this.idAdaptation = idAdaptation;
    }

    public Long getIdAnime() {
        return idAnime;
    }

    public void setIdAnime(Long idAnime) {
        this.idAnime = idAnime;
    }

    public Long getIdAdaptation() {
        return idAdaptation;
    }

    public void setIdAdaptation(Long idAdaptation) {
        this.idAdaptation = idAdaptation;
    }
}
