package net.v4lproik.googlanime.service.api.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@DiscriminatorColumn(name="type_jpa")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "Anime")
public class Entry {

    @Id
    @Column(name = "id")
    private Long id;

    private String type;

    private String title;

    private String englishTitle;

    private String japaneseTitle;

    private String synopsis;

    private String startedAiringDate;

    private String rank;

    private String popularity;

    private String score;

    private String finishedAiringDate;

    private String ageRating;

    private String posterImage;


    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "entry")
    @ElementCollection
    private Set<SynonymModel> synonyms;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name="Anime_has_Genre",
            joinColumns={@JoinColumn(name="idAnime", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="idGenre", referencedColumnName="id")
            })
    @ElementCollection
    private Set<GenreModel> genres;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name="Anime_has_Author",
            joinColumns={@JoinColumn(name="idAnime", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="idAuthor", referencedColumnName="id")
            })
    @ElementCollection
    private Set<AuthorModel> authors;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name="Anime_has_Tag",
            joinColumns={@JoinColumn(name="idAnime", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="idTag", referencedColumnName="id")
            })
    @ElementCollection
    private Set<TagModel> tags;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name="Anime_has_Character",
            joinColumns={@JoinColumn(name="idAnime", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="idCharacter", referencedColumnName="id")
            })
    @ElementCollection
    private Set<CharacterModel> characters;

    //Recursive dependecnies

    @Transient
    private List<AnimeIdModel> sequels;

    @Transient
    private List<AnimeIdModel> alternativeVersions;

    @Transient
    private List<AnimeIdModel> prequels;

    @Transient
    private List<AnimeIdModel> spinoff;

    @Transient
    private List<AnimeIdModel> sideStories;

    @Transient
    private List<AnimeIdModel> others;

    @Transient
    private List<AnimeIdModel> summaries;

    @Transient
    private List<AnimeIdModel> adaptations;

    @Transient
    private List<AnimeIdModel> parentStories;


    public Entry(Long id) {
        this.id = id;
    }

    public Entry() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnglishTitle() {
        return englishTitle;
    }

    public void setEnglishTitle(String englishTitle) {
        this.englishTitle = englishTitle;
    }

    public String getJapaneseTitle() {
        return japaneseTitle;
    }

    public void setJapaneseTitle(String japaneseTitle) {
        this.japaneseTitle = japaneseTitle;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getStartedAiringDate() {
        return startedAiringDate;
    }

    public void setStartedAiringDate(String startedAiringDate) {
        this.startedAiringDate = startedAiringDate;
    }

    public String getFinishedAiringDate() {
        return finishedAiringDate;
    }

    public void setFinishedAiringDate(String finishedAiringDate) {
        this.finishedAiringDate = finishedAiringDate;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Set<GenreModel> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenreModel> genres) {
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<CharacterModel> getCharacters() {
        return characters;
    }

    public void setCharacters(Set<CharacterModel> characters) {
        this.characters = characters;
    }

    public Set<AuthorModel> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorModel> authors) {
        this.authors = authors;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<AnimeIdModel> getSequels() {
        return sequels;
    }

    public void setSequels(List<AnimeIdModel> sequels) {
        this.sequels = sequels;
    }

    public List<AnimeIdModel> getAlternativeVersions() {
        return alternativeVersions;
    }

    public void setAlternativeVersions(List<AnimeIdModel> alternativeVersions) {
        this.alternativeVersions = alternativeVersions;
    }

    public List<AnimeIdModel> getPrequels() {
        return prequels;
    }

    public void setPrequels(List<AnimeIdModel> prequels) {
        this.prequels = prequels;
    }

    public List<AnimeIdModel> getSpinoff() {
        return spinoff;
    }

    public void setSpinoff(List<AnimeIdModel> spinoff) {
        this.spinoff = spinoff;
    }

    public List<AnimeIdModel> getSideStories() {
        return sideStories;
    }

    public void setSideStories(List<AnimeIdModel> sideStories) {
        this.sideStories = sideStories;
    }

    public List<AnimeIdModel> getOthers() {
        return others;
    }

    public void setOthers(List<AnimeIdModel> others) {
        this.others = others;
    }

    public List<AnimeIdModel> getSummaries() {
        return summaries;
    }

    public void setSummaries(List<AnimeIdModel> summaries) {
        this.summaries = summaries;
    }

    public List<AnimeIdModel> getAdaptations() {
        return adaptations;
    }

    public void setAdaptations(List<AnimeIdModel> adaptations) {
        this.adaptations = adaptations;
    }

    public Set<SynonymModel> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(Set<SynonymModel> synonyms) {
        this.synonyms = synonyms;
    }

    public Set<TagModel> getTags() {
        return tags;
    }

    public void setTags(Set<TagModel> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entry)) return false;
        Entry entry = (Entry) o;
        return Objects.equals(getType(), entry.getType()) &&
                Objects.equals(getTitle(), entry.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getTitle());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", id)
                .append("type", type)
                .append("title", title)
                .append("synonyms", synonyms)
                .append("englishTitle", englishTitle)
                .append("japaneseTitle", japaneseTitle)
                .append("synopsis", synopsis)
                .append("startedAiringDate", startedAiringDate)
                .append("rank", rank)
                .append("popularity", popularity)
                .append("score", score)
                .append("finishedAiringDate", finishedAiringDate)
                .append("ageRating", ageRating)
                .append("posterImage", posterImage)
                .append("genres", genres)
                .append("authors", authors)
                .append("tags", tags)
                .append("sequels", sequels)
                .append("alternativeVersions", alternativeVersions)
                .append("prequels", prequels)
                .append("spinoff", spinoff)
                .append("sideStories", sideStories)
                .append("others", others)
                .append("summaries", summaries)
                .append("adaptations", adaptations)
                .append("characters", characters)
                .toString();
    }
}
