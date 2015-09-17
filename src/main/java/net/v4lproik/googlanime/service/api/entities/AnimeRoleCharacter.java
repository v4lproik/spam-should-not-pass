package net.v4lproik.googlanime.service.api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table( name = "Anime_has_Character")
public class AnimeRoleCharacter implements Serializable {

    @Id
    private Long idAnime;

    @Id
    private Integer idCharacter;

    @Column(name = "role")
    private String role;

    public AnimeRoleCharacter() {
    }

    public AnimeRoleCharacter(Long idAnime, Integer idCharacter, String role) {
        this.idAnime = idAnime;
        this.idCharacter = idCharacter;
        this.role = role;
    }

    public Long getIdAnime() {
        return idAnime;
    }

    public void setIdAnime(Long idAnime) {
        this.idAnime = idAnime;
    }

    public Integer getIdCharacter() {
        return idCharacter;
    }

    public void setIdCharacter(Integer idCharacter) {
        this.idCharacter = idCharacter;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimeRoleCharacter that = (AnimeRoleCharacter) o;
        return Objects.equals(idAnime, that.idAnime) &&
                Objects.equals(idCharacter, that.idCharacter) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAnime, idCharacter, role);
    }
}
