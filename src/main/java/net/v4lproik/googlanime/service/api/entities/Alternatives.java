package net.v4lproik.googlanime.service.api.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Alternatives")
public class Alternatives implements Serializable{

    @Id
    private Long idAnime;

    @Id
    private Long idAlternative;

    public Alternatives(Long idAnime, Long idAlternative) {
        this.idAnime = idAnime;
        this.idAlternative = idAlternative;
    }

    public Long getIdAnime() {
        return idAnime;
    }

    public void setIdAnime(Long idAnime) {
        this.idAnime = idAnime;
    }

    public Long getIdAlternative() {
        return idAlternative;
    }

    public void setIdAlternative(Long idAlternative) {
        this.idAlternative = idAlternative;
    }
}
