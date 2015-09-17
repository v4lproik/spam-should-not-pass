package net.v4lproik.googlanime.service.api.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "SideStories")
public class SideStories implements Serializable{

    @Id
    private Long idAnime;

    @Id
    private Long idSideStory;

    public SideStories(Long idAnime, Long idSideStory) {
        this.idAnime = idAnime;
        this.idSideStory = idSideStory;
    }

    public Long getIdAnime() {
        return idAnime;
    }

    public void setIdAnime(Long idAnime) {
        this.idAnime = idAnime;
    }

    public Long getIdSideStory() {
        return idSideStory;
    }

    public void setIdSideStory(Long idSideStory) {
        this.idSideStory = idSideStory;
    }
}
