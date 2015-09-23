package net.v4lproik.googlanime.platform.dao.repositories;

import net.v4lproik.googlanime.platform.dao.api.AnimeDao;
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
public class AnimeRepository extends AbstractRepository implements AnimeDao{

    private static final Logger log = LoggerFactory.getLogger(AnimeRepository.class);
    public final SessionFactory sessionFactory;

    @Autowired
    public AnimeRepository(final SessionFactory sessionFactory) {
        super(Anime.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long save(Anime anime) {
        Transaction tx=currentSession().beginTransaction();

        Object idSave = currentSession().save(anime);

        currentSession().flush();
        tx.commit();

        return new Long(String.valueOf(idSave));
    }

    @Override
    public Long save(AnimeId anime) {
        Transaction tx=currentSession().beginTransaction();

        Object idSave = currentSession().save(anime);

        currentSession().flush();
        tx.commit();

        return new Long(String.valueOf(idSave));
    }

    @Override
    public void update(Anime anime){
        update(anime);
    }

    @Override
    public void saveOrUpdate(Anime anime) {
        Transaction tx;
        long id = anime.getId();

        // -----------------------------------------------------------------------------------------
        // Save ManyToMany/OneToMany Entities

        if (anime.getAuthors() != null) {
            for (Author author : anime.getAuthors()) {
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
                        idAuthor = save(author).intValue();
                        author.setId(idAuthor);
                    } else {
                        author.setId(authorDB.getId());
                        update(author);
                    }
                }
            }
        }

        if (anime.getCharacters() != null) {
            for (Character character : anime.getCharacters()) {
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

        if (anime.getProducers() != null) {
            for (Producer producer : anime.getProducers()) {
                Integer idProducer = producer.getId();

                if (producer.getId() == null) {
                    Producer producerDB = (Producer) getBySimpleCondition(Producer.class, "name", producer.getName());
                    if (producerDB == null) {
                        idProducer = (Integer) save(producer).intValue();
                        producer.setId(idProducer);
                    } else {
                        producer.setId(producerDB.getId());
                    }
                }
            }
        }

        if (anime.getGenres() != null) {
            for (Genre genre : anime.getGenres()) {
                Integer idGenre = genre.getId();

                if (genre.getId() != null) {
                    update(genre);
                } else {
                    Genre genreDB = (Genre) getBySimpleCondition(Genre.class, "name", genre.getName());
                    if (genreDB == null) {
                        idGenre = save(genre).intValue();
                        genre.setId(idGenre);
                    } else {
                        genre.setId(genreDB.getId());
                    }
                }
            }
        }

        if (anime.getTags() != null) {
            for (Tag tag : anime.getTags()) {
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

        if (anime.getSynonyms() != null) {
            for (Synonym synonym : anime.getSynonyms()) {
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

        currentSession().saveOrUpdate(anime);

        currentSession().flush();
        tx.commit();


        // -----------------------------------------------------------------------------------------
        // Save Alternatives, SidesStories etc

        if (anime.getSequels() != null){
            for (AnimeId sequel:anime.getSequels()){
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

        if (anime.getAlternativeVersions() != null){
            for (AnimeId alter:anime.getAlternativeVersions()){
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

        if (anime.getPrequels() != null){
            for (AnimeId prequel:anime.getPrequels()){
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

        if (anime.getSpinoff() != null){
            for (AnimeId spinOff:anime.getSpinoff()){
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

        if (anime.getSideStories() != null){
            for (AnimeId sideStory:anime.getSideStories()){
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

        if (anime.getOthers() != null){
            for (AnimeId other:anime.getOthers()){
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

        if (anime.getSummaries() != null){
            for (AnimeId summary:anime.getSummaries()){
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

        if (anime.getAdaptations() != null){
            for (AnimeId adaptation:anime.getAdaptations()) {
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

        if (anime.getAuthors() != null) {
            for (Author author : anime.getAuthors()) {
                Integer idAuthor = author.getId();

                if (author.getJobs() != null){
                    for (String job:author.getJobs()){
                        saveOrUpdate(new AnimeJobAuthor(id, idAuthor, job));
                    }
                }
            }
        }

        if (anime.getCharacters() != null) {
            for (Character character : anime.getCharacters()) {
                Integer idCharacter = character.getId();

                if (character.getRole() != null){
                    saveOrUpdate(new AnimeRoleCharacter(id, idCharacter, character.getRole()));
                }
            }
        }
    }

    @Override
    public Anime findById(Long id){
        return (Anime) getById(id);
    }

    @Override
    public Anime find(Anime anime){
        final Long id = anime.getId();
        final String type = anime.getType();
        final String showType = anime.getShowType();
        final String title = anime.getTitle();

        Transaction tx=currentSession().beginTransaction();
        Criteria criteria = currentSession().createCriteria(Anime.class);
        if (id != null) criteria.add(Restrictions.eq("id", id));
        if (type != null) criteria.add(Restrictions.eq("type", type));
        if (showType != null) criteria.add(Restrictions.eq("showType", showType));
        if (title != null) criteria.add(Restrictions.eq("title", title));

        Anime animeRes = (Anime) criteria.uniqueResult();
        tx.commit();

        return animeRes;
    }

    @Override
    public void delete(Anime anime) {
        delete(anime);
    }
}

