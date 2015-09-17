package net.v4lproik.googlanime.service.api.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue("anime")
public final class AnimeModel extends Entry{

    private String episodeCount;

    private String episodeLength;

    private String showType;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="Anime_has_Producer",
            joinColumns={@JoinColumn(name="idAnime", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="idProducer", referencedColumnName="id")
            })
    @ElementCollection
    private Set<ProducerModel> producers;

    public AnimeModel() {
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

    public Set<ProducerModel> getProducers() {
        return producers;
    }

    public void setProducers(Set<ProducerModel> producers) {
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