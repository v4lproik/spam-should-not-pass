package net.v4lproik.googlanime.service.api.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "SpinOffs")
public class SpinOffs implements Serializable{

    @Id
    private Long idAnime;

    @Id
    private Long idSpinOff;

    public SpinOffs(Long idAnime, Long idSpinOff) {
        this.idAnime = idAnime;
        this.idSpinOff = idSpinOff;
    }

    public Long getIdAnime() {
        return idAnime;
    }

    public void setIdAnime(Long idAnime) {
        this.idAnime = idAnime;
    }

    public Long getIdSpinOff() {
        return idSpinOff;
    }

    public void setIdSpinOff(Long idSpinOff) {
        this.idSpinOff = idSpinOff;
    }
}
