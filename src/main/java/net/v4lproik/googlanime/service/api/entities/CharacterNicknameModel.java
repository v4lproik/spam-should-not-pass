package net.v4lproik.googlanime.service.api.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Entity
@Table(name = "CharacterNicknames")
public class CharacterNicknameModel {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    private String nickname;

    @ManyToOne
    @JoinColumn(name="idCharacter")
    private CharacterModel character;

    public CharacterNicknameModel() {
    }

    public CharacterNicknameModel(String nickname) {
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

    public CharacterModel getCharacter() {
        return character;
    }

    public void setCharacter(CharacterModel character) {
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
