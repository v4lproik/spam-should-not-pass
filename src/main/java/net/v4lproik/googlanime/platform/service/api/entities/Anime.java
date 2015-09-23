package net.v4lproik.googlanime.platform.service.api.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue("anime")
public final class Anime extends Entry{

    private String episodeCount;

    private String episodeLength;

    private String showType;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="Anime_has_Producer",
            joinColumns={@JoinColumn(name="idAnime", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="idProducer", referencedColumnName="id")
            })
    @ElementCollection
    private Set<Producer> producers;

    public Anime() {
    }

    public String getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(String episodeCount) {
        this.episodeCount = episodeCount;
    }

    public String getEpisodeLength() {
        return episodeLength;
    }

    public void setEpisodeLength(String episodeLength) {
        this.episodeLength = episodeLength;
    }

    public Set<Producer> getProducers() {
        return producers;
    }

    public void setProducers(Set<Producer> producers) {
        this.producers = producers;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}