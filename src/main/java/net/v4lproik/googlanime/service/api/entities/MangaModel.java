package net.v4lproik.googlanime.service.api.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("manga")
public final class MangaModel extends Entry {

    private Integer nbVolumes;

    private Integer nbChapters;

    private String serialization;

    public Integer getNbVolumes() {
        return nbVolumes;
    }

    public void setNbVolumes(Integer nbVolumes) {
        this.nbVolumes = nbVolumes;
    }

    public Integer getNbChapters() {
        return nbChapters;
    }

    public void setNbChapters(Integer nbChapters) {
        this.nbChapters = nbChapters;
    }

    public String getSerialization() {
        return serialization;
    }

    public void setSerialization(String serialization) {
        this.serialization = serialization;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}