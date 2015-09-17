package net.v4lproik.googlanime.service.api.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table( name = "Anime_has_Producer")
public class AnimeProducer implements Serializable {

    @Id
    private Long idAnime;

    @Id
    private Integer idProducer;

    public AnimeProducer() {
    }

    public AnimeProducer(Long idAnime, Integer idProducer) {
        this.idAnime = idAnime;
        this.idProducer = idProducer;
    }

    public Long getIdAnime() {
        return idAnime;
    }

    public void setIdAnime(Long idAnime) {
        this.idAnime = idAnime;
    }

    public Integer getIdProducer() {
        return idProducer;
    }

    public void setIdProducer(Integer idProducer) {
        this.idProducer = idProducer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("idAnime", idAnime)
                .append("idProducer", idProducer)
                .toString();
    }
}
