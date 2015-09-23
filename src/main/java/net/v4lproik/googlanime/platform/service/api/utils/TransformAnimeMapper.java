package net.v4lproik.googlanime.platform.service.api.utils;

import net.v4lproik.googlanime.platform.service.api.entities.*;
import net.v4lproik.googlanime.platform.service.api.entities.Character;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TransformAnimeMapper extends TransformAbstractMapper {

    public final static String DATE_FORMAT = "MMM dd, yyyy";


    public Anime transformMyAnimeListAnimeDependencyToDAO(com.github.v4lproik.myanimelist.api.models.Anime myAnimeListEntryDependency){
        Anime anime;

        ModelMapper modelMapper = new ModelMapper();
        anime = modelMapper.map(myAnimeListEntryDependency, Anime.class);

        String[] genres = myAnimeListEntryDependency.getGenres();
        if (genres != null){
            Set<Genre> genresModel = new HashSet<>();
            for (String genre:genres){
                Genre genreModel = new Genre();
                genreModel.setName(genre);
                genresModel.add(genreModel);
            }
            anime.setGenres(genresModel);
        }

        String[] producers = myAnimeListEntryDependency.getProducers();
        if (producers != null){
            Set<Producer> producersModel = new HashSet<>();
            for (String producer:producers){
                Producer producerModel = new Producer();
                producerModel.setName(producer);
                producersModel.add(producerModel);
            }
            anime.setProducers(producersModel);
        }

        List<com.github.v4lproik.myanimelist.api.models.Character> characters = myAnimeListEntryDependency.getCharacters();
        if (characters != null) {
            Character characterModel;
            Set<Character> charactersModel = new HashSet<>();
            for (com.github.v4lproik.myanimelist.api.models.Character character : characters) {
                characterModel = new Character();
                characterModel.setRole(character.getRole());
                characterModel.setFirstName(character.getFirstName());
                characterModel.setJapaneseName(character.getJapaneseName());
                characterModel.setLastName(character.getLastName());

                Set<CharacterNickname> characterNicknamesModel = new HashSet<>();
                if (character.getNickNames() != null){
                    for (String nickname:character.getNickNames()){
                        characterNicknamesModel.add(new CharacterNickname(nickname));
                    }
                }
                characterModel.setNicknames(characterNicknamesModel);

                charactersModel.add(characterModel);
            }
            anime.setCharacters(charactersModel);
        }

        List<com.github.v4lproik.myanimelist.api.models.Author> authors = myAnimeListEntryDependency.getAuthors();
        if (authors != null) {
            Author authorModel;
            Set<Author> authorsModel = new HashSet<>();
            for (com.github.v4lproik.myanimelist.api.models.Author author : authors) {
                authorModel = new Author();
                authorModel.setFirstName(author.getFirstName());
                authorModel.setLastName(author.getLastName());

                Set<String> jobs = new HashSet<>();
                if (author.getJob() != null){
                    for (String job:author.getJob()){
                        jobs.add(job);
                    }
                }
                authorModel.setJobs(jobs);

                authorsModel.add(authorModel);
            }
            anime.setAuthors(authorsModel);
        }

        if (myAnimeListEntryDependency.getFinishedAiringDate() != null){
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
                Date date = formatter.parse(myAnimeListEntryDependency.getFinishedAiringDate());
                LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
                anime.setFinishedAiringDate(dateTime.toString());
            } catch (ParseException e) {
                anime.setFinishedAiringDate(null);
            }
        }

        if (myAnimeListEntryDependency.getStartedAiringDate() != null){
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
                Date date = formatter.parse(myAnimeListEntryDependency.getStartedAiringDate());
                LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
                anime.setStartedAiringDate(dateTime.toString());
            } catch (ParseException e) {
                anime.setStartedAiringDate(null);
            }
        }

        String[] synonyms = myAnimeListEntryDependency.getSynonyms();
        if (synonyms != null) {
            Synonym synonymModel;
            Set<Synonym> synonymsModel = new HashSet<>();
            for (String synonym : synonyms) {
                synonymModel = new Synonym();
                synonymModel.setTitle(synonym);
                synonymModel.setEntry(anime);
                synonymsModel.add(synonymModel);
            }
            anime.setSynonyms(synonymsModel);
        }

        String[] tags = myAnimeListEntryDependency.getTags();
        if (tags != null) {
            Tag tagModel;
            Set<Tag> tagsModel = new HashSet<>();
            for (String tag : tags) {
                tagModel = new Tag();
                tagModel.setName(tag);
                tagsModel.add(tagModel);
            }
            anime.setTags(tagsModel);
        }

        return anime;
    }

//    public Anime transformMyAnimeListAnimeToDAO(Anime myAnimeListAnime){
//        Anime anime;
//
//        ModelMapper modelMapper = new ModelMapper();
//        anime = modelMapper.map(myAnimeListAnime, Anime.class);
//
//        String[] genres = myAnimeListAnime.getGenres();
//        if (genres != null){
//            Set<Genre> genresModel = new HashSet<>();
//            for (String genre:genres){
//                Genre genreModel = new Genre();
//                genreModel.setName(genre);
//                genresModel.add(genreModel);
//            }
//            anime.setGenres(genresModel);
//        }
//
//        String[] producers = myAnimeListAnime.getProducers();
//        if (producers != null){
//            Set<Producer> producersModel = new HashSet<>();
//            for (String producer:producers){
//                Producer producerModel = new Producer();
//                producerModel.setName(producer);
//                producersModel.add(producerModel);
//            }
//            anime.setProducers(producersModel);
//        }
//
//        List<Character> characters = myAnimeListAnime.getCharacters();
//        if (characters != null) {
//            Character characterModel;
//            Set<Character> charactersModel = new HashSet<>();
//            for (Character character : characters) {
//                characterModel = new Character();
//                characterModel.setRole(character.getRole());
//                characterModel.setFirstName(character.getFirstName());
//                characterModel.setJapaneseName(character.getJapaneseName());
//                characterModel.setLastName(character.getLastName());
//
//                Set<CharacterNickname> characterNicknamesModel = new HashSet<>();
//                if (character.getNickNames() != null){
//                    for (String nickname:character.getNickNames()){
//                        characterNicknamesModel.add(new CharacterNickname(nickname));
//                    }
//                }
//                characterModel.setNicknames(characterNicknamesModel);
//
//                charactersModel.add(characterModel);
//            }
//            anime.setCharacters(charactersModel);
//        }
//
//        List<Author> authors = myAnimeListAnime.getAuthors();
//        if (authors != null) {
//            Author authorModel;
//            Set<Author> authorsModel = new HashSet<>();
//            for (Author author : authors) {
//                authorModel = new Author();
//                authorModel.setFirstName(author.getFirstName());
//                authorModel.setLastName(author.getLastName());
//
//                Set<String> jobs = new HashSet<>();
//                if (author.getJob() != null){
//                    for (String job:author.getJob()){
//                        jobs.add(job);
//                    }
//                }
//                authorModel.setJobs(jobs);
//
//                authorsModel.add(authorModel);
//            }
//            anime.setAuthors(authorsModel);
//        }
//
//        if (myAnimeListAnime.getFinishedAiringDate() != null){
//            try {
//                SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
//                Date date = formatter.parse(myAnimeListAnime.getFinishedAiringDate());
//                LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
//                anime.setFinishedAiringDate(dateTime.toString());
//            } catch (ParseException e) {
//                anime.setFinishedAiringDate(null);
//            }
//        }
//
//        if (myAnimeListAnime.getStartedAiringDate() != null){
//            try {
//                SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
//                Date date = formatter.parse(myAnimeListAnime.getStartedAiringDate());
//                LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
//                anime.setStartedAiringDate(dateTime.toString());
//            } catch (ParseException e) {
//                anime.setStartedAiringDate(null);
//            }
//        }
//
//        String[] synonyms = myAnimeListAnime.getSynonyms();
//        if (synonyms != null) {
//            Synonym synonymModel;
//            Set<Synonym> synonymsModel = new HashSet<>();
//            for (String synonym : synonyms) {
//                synonymModel = new Synonym();
//                synonymModel.setTitle(synonym);
//                synonymModel.setEntry(anime);
//                synonymsModel.add(synonymModel);
//            }
//            anime.setSynonyms(synonymsModel);
//        }
//
//        String[] tags = myAnimeListAnime.getTags();
//        if (tags != null) {
//            Tag tagModel;
//            Set<Tag> tagsModel = new HashSet<>();
//            for (String tag : tags) {
//                tagModel = new Tag();
//                tagModel.setName(tag);
//                tagsModel.add(tagModel);
//            }
//            anime.setTags(tagsModel);
//        }
//
//        return anime;
//    }
}
