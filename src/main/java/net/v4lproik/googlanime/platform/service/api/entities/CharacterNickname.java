package net.v4lproik.googlanime.platform.service.api.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Entity
@Table(name = "CharacterNicknames")
public class CharacterNickname {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    private String nickname;

    @ManyToOne
    @JoinColumn(name="idCharacter")
    private Character character;

    public CharacterNickname() {
    }

    public CharacterNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", id)
                .append("nickname", nickname)
                .append("character", character)
                .toString();
    }
}
