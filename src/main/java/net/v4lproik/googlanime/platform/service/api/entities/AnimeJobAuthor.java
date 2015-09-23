package net.v4lproik.googlanime.platform.service.api.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table( name = "Anime_has_Author")
public class AnimeJobAuthor implements Serializable {

    @Id
    private Long idAnime;

    @Id
    private Integer idAuthor;

    private String job;

    public AnimeJobAuthor() {
    }

    public AnimeJobAuthor(Long idAnime, Integer idAuthor, String job) {
        this.idAnime = idAnime;
        this.idAuthor = idAuthor;
        this.job = job;
    }

    public Long getIdAnime() {
        return idAnime;
    }

    public void setIdAnime(Long idAnime) {
        this.idAnime = idAnime;
    }

    public Integer getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(Integer idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("idAnime", idAnime)
                .append("idAuthor", idAuthor)
                .append("job", job)
                .toString();
    }
}
