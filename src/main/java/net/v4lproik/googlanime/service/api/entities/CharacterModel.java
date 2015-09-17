package net.v4lproik.googlanime.service.api.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "CharacterT")
public class CharacterModel {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    private String firstName;

    private String lastName;

    private String japaneseName;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinTable(name="Anime_has_Character",
            joinColumns={@JoinColumn(name="idCharacter", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="idAnime", referencedColumnName="id")
            })
    private Set<Entry> entries;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "character", orphanRemoval = true)
    @ElementCollection
    private Set<CharacterNicknameModel> nicknames;

    @Transient
    private String role;

    public CharacterModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJapaneseName() {
        return japaneseName;
    }

    public void setJapaneseName(String japaneseName) {
        this.japaneseName = japaneseName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<CharacterNicknameModel> getNicknames() {
        return nicknames;
    }

    public void setNicknames(Set<CharacterNicknameModel> nicknames) {
        this.nicknames = nicknames;
    }

    public Set<Entry> getEntries() {
        return entries;
    }

    public void setEntries(Set<Entry> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", id)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("japaneseName", japaneseName)
                .append("nicknames", nicknames)
                .append("role", role)
                .toString();
    }
}
