package net.v4lproik.googlanime.platform.dao.repositories;

import net.v4lproik.googlanime.platform.dao.api.MangaDao;
import net.v4lproik.googlanime.platform.service.api.entities.*;
import net.v4lproik.googlanime.platform.service.api.entities.Character;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MangaRepository extends AbstractRepository implements MangaDao{

    private static final Logger log = LoggerFactory.getLogger(MangaRepository.class);

    public final SessionFactory sessionFactory;

    @Autowired
    public MangaRepository(final SessionFactory sessionFactory) {
        super(Manga.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long save(Manga manga) {
        Transaction tx=currentSession().beginTransaction();

        Object idSave = currentSession().save(manga);

        currentSession().flush();
        tx.commit();

        return new Long(String.valueOf(idSave));
    }

    @Override
    public Long save(AnimeId manga) {
        Transaction tx=currentSession().beginTransaction();

        Object idSave = currentSession().save(manga);

        currentSession().flush();
        tx.commit();

        return new Long(String.valueOf(idSave));
    }

    @Override
    public void saveOrUpdate(Manga manga) {
        Transaction tx;
        long id = manga.getId();

        // -----------------------------------------------------------------------------------------
        // Save ManyToMany/OneToMany Entities

        if (manga.getAuthors() != null) {
            for (Author author : manga.getAuthors()) {
                Integer idAuthor = author.getId();

                if (author.getId() != null) {
                    update(author);
                } else {
                    Map<String, String> conditions = new HashMap<String, String>() {{
                        put("firstName", author.getFirstName());
                        put("lastName", author.getLastName());
                    }};

                    Author authorDB = (Author) getBySimpleCondition(Author.class, conditions);

                    if (authorDB == null) {
                        idAuthor = (Integer) save(author).intValue();
                        author.setId(idAuthor);
                    } else {
                        author.setId(authorDB.getId());
                        update(author);
                    }
                }
            }
        }

        if (manga.getCharacters() != null) {
            for (Character character : manga.getCharacters()) {
                Integer idCharacter = character.getId();

                if (character.getId() != null) {
                    update(character);
                } else {
                    Map<String, String> conditions = new HashMap<String, String>() {{
                        put("firstName", character.getFirstName());
                        put("lastName", character.getLastName());
                    }};

                    Character characterDB = (Character) getBySimpleCondition(Character.class, conditions);

                    if (characterDB == null) {
                        idCharacter = (Integer) save(character).intValue();
                        character.setId(idCharacter);
                    } else {
                        character.setId(characterDB.getId());
                        update(character);
                    }
                }
            }
        }


        if (manga.getGenres() != null) {
            for (Genre genre : manga.getGenres()) {
                Integer idGenre = genre.getId();

                if (genre.getId() != null) {
                    update(genre);
                } else {
                    Genre genreDB = (Genre) getBySimpleCondition(Genre.class, "name", genre.getName());
                    if (genreDB == null) {
                        idGenre = (Integer) save(genre).intValue();
                        genre.setId(idGenre);
                    } else {
                        genre.setId(genreDB.getId());
                    }
                }
            }
        }

        if (manga.getTags() != null) {
            for (Tag tag : manga.getTags()) {
                Integer idTag = tag.getId();

                if (tag.getId() != null) {
                    update(tag);
                } else {
                    Tag tagDB = (Tag) getBySimpleCondition(Tag.class, "name", tag.getName());
                    if (tagDB == null) {
                        idTag = (Integer) save(tag).intValue();
                        tag.setId(idTag);
                    } else {
                        tag.setId(tagDB.getId());
                    }
                }
            }
        }

        if (manga.getSynonyms() != null) {
            for (Synonym synonym : manga.getSynonyms()) {
                Integer idSynonym = synonym.getId();

                if (synonym.getId() != null) {
                    update(synonym);
                } else {

                    Map<String, Object> conditions = new HashMap<String, Object>() {{
                        put("title", synonym.getTitle());
                        put("entry", synonym.getEntry());
                    }};
                    Synonym synonymDB = (Synonym) getBySimpleConditionObject(Synonym.class, conditions);
                    if (synonymDB == null) {
                        idSynonym = (Integer) save(synonym).intValue();
                        synonym.setId(idSynonym);
                    } else {
                        synonym.setId(synonymDB.getId());
                    }
                }
            }
        }

        // -----------------------------------------------------------------------------------------
        // Save Anime

        tx=currentSession().beginTransaction();

        currentSession().saveOrUpdate(manga);

        currentSession().flush();
        tx.commit();


        // -----------------------------------------------------------------------------------------
        // Save Alternatives, SidesStories etc

        if (manga.getSequels() != null){
            for (AnimeId sequel:manga.getSequels()){
                if (sequel.getId() != id){

                    if (findById(sequel.getId()) != null){
                        update(sequel);
                    }else{
                        merge(sequel);
                    }

                    saveOrUpdate(new Sequels(id, sequel.getId()));
                }
            }
        }

        if (manga.getAlternativeVersions() != null){
            for (AnimeId alter:manga.getAlternativeVersions()){
                if (alter.getId() != id) {

                    if (findById(alter.getId()) != null){
                        update(alter);
                    }else{
                        merge(alter);
                    }

                    saveOrUpdate(new Sequels(id, alter.getId()));
                }
            }
        }

        if (manga.getPrequels() != null){
            for (AnimeId prequel:manga.getPrequels()){
                if (prequel.getId() != id){

                    if (findById(prequel.getId()) != null){
                        update(prequel);
                    }else{
                        merge(prequel);
                    }

                    saveOrUpdate(new Prequels(id, prequel.getId()));
                }
            }
        }

        if (manga.getSpinoff() != null){
            for (AnimeId spinOff:manga.getSpinoff()){
                if (spinOff.getId() != id){

                    if (findById(spinOff.getId()) != null){
                        update(spinOff);
                    }else{
                        merge(spinOff);
                    }

                    saveOrUpdate(new SpinOffs(id, spinOff.getId()));
                }
            }
        }

        if (manga.getSideStories() != null){
            for (AnimeId sideStory:manga.getSideStories()){
                if (sideStory.getId() != id){

                    if (findById(sideStory.getId()) != null){
                        update(sideStory);
                    }else{
                        merge(sideStory);
                    }

                    saveOrUpdate(new SideStories(id, sideStory.getId()));
                }
            }
        }

        if (manga.getOthers() != null){
            for (AnimeId other:manga.getOthers()){
                if (other.getId() != id){

                    if (findById(other.getId()) != null){
                        update(other);
                    }else{
                        merge(other);
                    }

                    saveOrUpdate(new Others(id, other.getId()));
                }
            }
        }

        if (manga.getSummaries() != null){
            for (AnimeId summary:manga.getSummaries()){
                if (summary.getId() != id){

                    if (findById(summary.getId()) != null){
                        update(summary);
                    }else{
                        merge(summary);
                    }

                    saveOrUpdate(new Summaries(id, summary.getId()));
                }
            }
        }

        if (manga.getAdaptations() != null){
            for (AnimeId adaptation:manga.getAdaptations()) {
                if (adaptation.getId() != id){

                    if (findById(adaptation.getId()) != null){
                        update(adaptation);
                    }else{
                        merge(adaptation);
                    }

                    saveOrUpdate(new Adaptations(id, adaptation.getId()));
                }
            }
        }



        // -----------------------------------------------------------------------------------------
        // Save ManyToMany Extra Columns

        if (manga.getAuthors() != null) {
            for (Author author : manga.getAuthors()) {
                Integer idAuthor = author.getId();

                if (author.getJobs() != null){
                    for (String job:author.getJobs()){
                        saveOrUpdate(new AnimeJobAuthor(id, idAuthor, job));
                    }
                }
            }
        }

        if (manga.getCharacters() != null) {
            for (Character character : manga.getCharacters()) {
                Integer idCharacter = character.getId();

                if (character.getRole() != null){
                    saveOrUpdate(new AnimeRoleCharacter(id, idCharacter, character.getRole()));
                }
            }
        }
    }

    @Override
    public Manga findById(Long id){
        return (Manga) getById(id);
    }

    @Override
    public Manga find(Manga manga){
        final Long id = manga.getId();
        final String type = manga.getType();
        final String title = manga.getTitle();

        Transaction tx=currentSession().beginTransaction();
        Criteria criteria = currentSession().createCriteria(Manga.class);
        if (id != null) criteria.add(Restrictions.eq("id", id));
        if (type != null) criteria.add(Restrictions.eq("type", type));
        if (title != null) criteria.add(Restrictions.eq("title", title));

        Manga mangaRes = (Manga) criteria.uniqueResult();
        tx.commit();

        return mangaRes;
    }

    @Override
    public void delete(Manga anime) {
        delete(anime);
    }
}

