package net.v4lproik.googlanime.service.api.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Others")
public class Others implements Serializable{

    @Id
    private Long idAnime;

    @Id
    private Long idOther;

    public Others(Long idAnime, Long idOther) {
        this.idAnime = idAnime;
        this.idOther = idOther;
    }

    public Long getIdAnime() {
        return idAnime;
    }

    public void setIdAnime(Long idAnime) {
        this.idAnime = idAnime;
    }

    public Long getIdOther() {
        return idOther;
    }

    public void setIdOther(Long idOther) {
        this.idOther = idOther;
    }
}
