package net.v4lproik.googlanime.service.api.entities;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="Producer")
public class ProducerModel {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    private String name;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "producers")
    private Set<AnimeModel> animes;

    public ProducerModel() {
    }

    public ProducerModel(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AnimeModel> getAnimes() {
        return animes;
    }

    public void setAnimes(Set<AnimeModel> animes) {
        this.animes = animes;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .toString();
    }
}
