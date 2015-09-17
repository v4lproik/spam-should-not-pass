package net.v4lproik.googlanime.dao.repositories;

import net.v4lproik.googlanime.dao.api.AnimeDao;
import net.v4lproik.googlanime.service.api.entities.*;
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
        super(AnimeModel.class, sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long save(AnimeModel anime) {
        Transaction tx=currentSession().beginTransaction();

        Object idSave = currentSession().save(anime);

        currentSession().flush();
        tx.commit();

        return new Long(String.valueOf(idSave));
    }

    @Override
    public Long save(AnimeIdModel anime) {
        Transaction tx=currentSession().beginTransaction();

        Object idSave = currentSession().save(anime);

        currentSession().flush();
        tx.commit();

        return new Long(String.valueOf(idSave));
    }

    @Override
    public void update(AnimeModel anime){
        update(anime);
    }

    @Override
    public void saveOrUpdate(AnimeModel anime) {
        Transaction tx;
        long id = anime.getId();

        // -----------------------------------------------------------------------------------------
        // Save ManyToMany/OneToMany Entities

        if (anime.getAuthors() != null) {
            for (AuthorModel author : anime.getAuthors()) {
                Integer idAuthor = author.getId();

                if (author.getId() != null) {
                    update(author);
                } else {
                    Map<String, String> conditions = new HashMap<String, String>() {{
                        put("firstName", author.getFirstName());
                        put("lastName", author.getLastName());
                    }};

                    AuthorModel authorDB = (AuthorModel) getBySimpleCondition(AuthorModel.class, conditions);

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
            for (CharacterModel character : anime.getCharacters()) {
                Integer idCharacter = character.getId();

                if (character.getId() != null) {
                    update(character);
                } else {
                    Map<String, String> conditions = new HashMap<String, String>() {{
                        put("firstName", character.getFirstName());
                        put("lastName", character.getLastName());
                    }};

                    CharacterModel characterDB = (CharacterModel) getBySimpleCondition(CharacterModel.class, conditions);

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
            for (ProducerModel producer : anime.getProducers()) {
                Integer idProducer = producer.getId();

                if (producer.getId() == null) {
                    ProducerModel producerDB = (ProducerModel) getBySimpleCondition(ProducerModel.class, "name", producer.getName());
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
            for (GenreModel genre : anime.getGenres()) {
                Integer idGenre = genre.getId();

                if (genre.getId() != null) {
                    update(genre);
                } else {
                    GenreModel genreDB = (GenreModel) getBySimpleCondition(GenreModel.class, "name", genre.getName());
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
            for (TagModel tag : anime.getTags()) {
                Integer idTag = tag.getId();

                if (tag.getId() != null) {
                    update(tag);
                } else {
                    TagModel tagDB = (TagModel) getBySimpleCondition(TagModel.class, "name", tag.getName());
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
            for (SynonymModel synonym : anime.getSynonyms()) {
                Integer idSynonym = synonym.getId();

                if (synonym.getId() != null) {
                    update(synonym);
                } else {

                    Map<String, Object> conditions = new HashMap<String, Object>() {{
                        put("title", synonym.getTitle());
                        put("entry", synonym.getEntry());
                    }};
                    SynonymModel synonymDB = (SynonymModel) getBySimpleConditionObject(SynonymModel.class, conditions);
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
            for (AnimeIdModel sequel:anime.getSequels()){
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
            for (AnimeIdModel alter:anime.getAlternativeVersions()){
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
            for (AnimeIdModel prequel:anime.getPrequels()){
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
            for (AnimeIdModel spinOff:anime.getSpinoff()){
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
            for (AnimeIdModel sideStory:anime.getSideStories()){
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
            for (AnimeIdModel other:anime.getOthers()){
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
            for (AnimeIdModel summary:anime.getSummaries()){
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
            for (AnimeIdModel adaptation:anime.getAdaptations()) {
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
            for (AuthorModel author : anime.getAuthors()) {
                Integer idAuthor = author.getId();

                if (author.getJobs() != null){
                    for (String job:author.getJobs()){
                        saveOrUpdate(new AnimeJobAuthor(id, idAuthor, job));
                    }
                }
            }
        }

        if (anime.getCharacters() != null) {
            for (CharacterModel character : anime.getCharacters()) {
                Integer idCharacter = character.getId();

                if (character.getRole() != null){
                    saveOrUpdate(new AnimeRoleCharacter(id, idCharacter, character.getRole()));
                }
            }
        }
    }

    @Override
    public AnimeModel findById(Long id){
        return (AnimeModel) getById(id);
    }

    @Override
    public AnimeModel find(AnimeModel anime){
        final Long id = anime.getId();
        final String type = anime.getType();
        final String showType = anime.getShowType();
        final String title = anime.getTitle();

        Transaction tx=currentSession().beginTransaction();
        Criteria criteria = currentSession().createCriteria(AnimeModel.class);
        if (id != null) criteria.add(Restrictions.eq("id", id));
        if (type != null) criteria.add(Restrictions.eq("type", type));
        if (showType != null) criteria.add(Restrictions.eq("showType", showType));
        if (title != null) criteria.add(Restrictions.eq("title", title));

        AnimeModel animeRes = (AnimeModel) criteria.uniqueResult();
        tx.commit();

        return animeRes;
    }

    @Override
    public void delete(AnimeModel anime) {
        delete(anime);
    }
}

