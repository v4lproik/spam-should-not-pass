package net.v4lproik.googlanime.service.api.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Summaries")
public class Summaries implements Serializable{

    @Id
    private Long idAnime;

    @Id
    private Long idSummary;

    public Summaries(Long idAnime, Long idSummary) {
        this.idAnime = idAnime;
        this.idSummary = idSummary;
    }

    public Long getIdAnime() {
        return idAnime;
    }

    public void setIdAnime(Long idAnime) {
        this.idAnime = idAnime;
    }

    public Long getIdSummary() {
        return idSummary;
    }

    public void setIdSummary(Long idSummary) {
        this.idSummary = idSummary;
    }
}
