package net.v4lproik.googlanime.service.api.utils;

import com.github.v4lproik.myanimelist.api.models.Anime;
import com.github.v4lproik.myanimelist.api.models.Author;
import com.github.v4lproik.myanimelist.api.models.Character;
import net.v4lproik.googlanime.service.api.entities.*;
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


    public AnimeModel transformMyAnimeListAnimeDependencyToDAO(Anime myAnimeListEntryDependency){
        AnimeModel anime;

        ModelMapper modelMapper = new ModelMapper();
        anime = modelMapper.map(myAnimeListEntryDependency, AnimeModel.class);

        String[] genres = myAnimeListEntryDependency.getGenres();
        if (genres != null){
            Set<GenreModel> genresModel = new HashSet<>();
            for (String genre:genres){
                GenreModel genreModel = new GenreModel();
                genreModel.setName(genre);
                genresModel.add(genreModel);
            }
            anime.setGenres(genresModel);
        }

        String[] producers = myAnimeListEntryDependency.getProducers();
        if (producers != null){
            Set<ProducerModel> producersModel = new HashSet<>();
            for (String producer:producers){
                ProducerModel producerModel = new ProducerModel();
                producerModel.setName(producer);
                producersModel.add(producerModel);
            }
            anime.setProducers(producersModel);
        }

        List<Character> characters = myAnimeListEntryDependency.getCharacters();
        if (characters != null) {
            CharacterModel characterModel;
            Set<CharacterModel> charactersModel = new HashSet<>();
            for (Character character : characters) {
                characterModel = new CharacterModel();
                characterModel.setRole(character.getRole());
                characterModel.setFirstName(character.getFirstName());
                characterModel.setJapaneseName(character.getJapaneseName());
                characterModel.setLastName(character.getLastName());

                Set<CharacterNicknameModel> characterNicknamesModel = new HashSet<>();
                if (character.getNickNames() != null){
                    for (String nickname:character.getNickNames()){
                        characterNicknamesModel.add(new CharacterNicknameModel(nickname));
                    }
                }
                characterModel.setNicknames(characterNicknamesModel);

                charactersModel.add(characterModel);
            }
            anime.setCharacters(charactersModel);
        }

        List<Author> authors = myAnimeListEntryDependency.getAuthors();
        if (authors != null) {
            AuthorModel authorModel;
            Set<AuthorModel> authorsModel = new HashSet<>();
            for (Author author : authors) {
                authorModel = new AuthorModel();
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
            SynonymModel synonymModel;
            Set<SynonymModel> synonymsModel = new HashSet<>();
            for (String synonym : synonyms) {
                synonymModel = new SynonymModel();
                synonymModel.setTitle(synonym);
                synonymModel.setEntry(anime);
                synonymsModel.add(synonymModel);
            }
            anime.setSynonyms(synonymsModel);
        }

        String[] tags = myAnimeListEntryDependency.getTags();
        if (tags != null) {
            TagModel tagModel;
            Set<TagModel> tagsModel = new HashSet<>();
            for (String tag : tags) {
                tagModel = new TagModel();
                tagModel.setName(tag);
                tagsModel.add(tagModel);
            }
            anime.setTags(tagsModel);
        }

        return anime;
    }

//    public AnimeModel transformMyAnimeListAnimeToDAO(Anime myAnimeListAnime){
//        AnimeModel anime;
//
//        ModelMapper modelMapper = new ModelMapper();
//        anime = modelMapper.map(myAnimeListAnime, AnimeModel.class);
//
//        String[] genres = myAnimeListAnime.getGenres();
//        if (genres != null){
//            Set<GenreModel> genresModel = new HashSet<>();
//            for (String genre:genres){
//                GenreModel genreModel = new GenreModel();
//                genreModel.setName(genre);
//                genresModel.add(genreModel);
//            }
//            anime.setGenres(genresModel);
//        }
//
//        String[] producers = myAnimeListAnime.getProducers();
//        if (producers != null){
//            Set<ProducerModel> producersModel = new HashSet<>();
//            for (String producer:producers){
//                ProducerModel producerModel = new ProducerModel();
//                producerModel.setName(producer);
//                producersModel.add(producerModel);
//            }
//            anime.setProducers(producersModel);
//        }
//
//        List<Character> characters = myAnimeListAnime.getCharacters();
//        if (characters != null) {
//            CharacterModel characterModel;
//            Set<CharacterModel> charactersModel = new HashSet<>();
//            for (Character character : characters) {
//                characterModel = new CharacterModel();
//                characterModel.setRole(character.getRole());
//                characterModel.setFirstName(character.getFirstName());
//                characterModel.setJapaneseName(character.getJapaneseName());
//                characterModel.setLastName(character.getLastName());
//
//                Set<CharacterNicknameModel> characterNicknamesModel = new HashSet<>();
//                if (character.getNickNames() != null){
//                    for (String nickname:character.getNickNames()){
//                        characterNicknamesModel.add(new CharacterNicknameModel(nickname));
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
//            AuthorModel authorModel;
//            Set<AuthorModel> authorsModel = new HashSet<>();
//            for (Author author : authors) {
//                authorModel = new AuthorModel();
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
//            SynonymModel synonymModel;
//            Set<SynonymModel> synonymsModel = new HashSet<>();
//            for (String synonym : synonyms) {
//                synonymModel = new SynonymModel();
//                synonymModel.setTitle(synonym);
//                synonymModel.setEntry(anime);
//                synonymsModel.add(synonymModel);
//            }
//            anime.setSynonyms(synonymsModel);
//        }
//
//        String[] tags = myAnimeListAnime.getTags();
//        if (tags != null) {
//            TagModel tagModel;
//            Set<TagModel> tagsModel = new HashSet<>();
//            for (String tag : tags) {
//                tagModel = new TagModel();
//                tagModel.setName(tag);
//                tagsModel.add(tagModel);
//            }
//            anime.setTags(tagsModel);
//        }
//
//        return anime;
//    }
}
