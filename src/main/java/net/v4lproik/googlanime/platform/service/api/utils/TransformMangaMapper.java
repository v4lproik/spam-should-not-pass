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
public class TransformMangaMapper extends TransformAbstractMapper {

    public final static String DATE_FORMAT = "MMM dd, yyyy";

    public Manga transformMyAnimeListMangaDependencyToDAO(com.github.v4lproik.myanimelist.api.models.Manga myAnimeListMangaDependency){
        Manga manga = new Manga();

        ModelMapper modelMapper = new ModelMapper();
        manga = modelMapper.map(myAnimeListMangaDependency, Manga.class);

        String[] genres = myAnimeListMangaDependency.getGenres();
        if (genres != null){
            Set<Genre> genresModel = new HashSet<>();
            for (String genre:genres){
                Genre genreModel = new Genre();
                genreModel.setName(genre);
                genresModel.add(genreModel);
            }
            manga.setGenres(genresModel);
        }

        List<com.github.v4lproik.myanimelist.api.models.Character> characters = myAnimeListMangaDependency.getCharacters();
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
            manga.setCharacters(charactersModel);
        }

        List<com.github.v4lproik.myanimelist.api.models.Author> authors = myAnimeListMangaDependency.getAuthors();
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
            manga.setAuthors(authorsModel);
        }

        if (myAnimeListMangaDependency.getFinishedAiringDate() != null){
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
                Date date = formatter.parse(myAnimeListMangaDependency.getFinishedAiringDate());
                LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
                manga.setFinishedAiringDate(dateTime.toString());
            } catch (ParseException e) {
                manga.setFinishedAiringDate(null);
            }
        }

        if (myAnimeListMangaDependency.getStartedAiringDate() != null){
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
                Date date = formatter.parse(myAnimeListMangaDependency.getStartedAiringDate());
                LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
                manga.setStartedAiringDate(dateTime.toString());
            } catch (ParseException e) {
                manga.setStartedAiringDate(null);
            }
        }

        String[] synonyms = myAnimeListMangaDependency.getSynonyms();
        if (synonyms != null) {
            Synonym synonymModel;
            Set<Synonym> synonymsModel = new HashSet<>();
            for (String synonym : synonyms) {
                synonymModel = new Synonym();
                synonymModel.setTitle(synonym);
                synonymModel.setEntry(manga);
                synonymsModel.add(synonymModel);
            }
            manga.setSynonyms(synonymsModel);
        }

        String[] tags = myAnimeListMangaDependency.getTags();
        if (tags != null) {
            Tag tagModel;
            Set<Tag> tagsModel = new HashSet<>();
            for (String tag : tags) {
                tagModel = new Tag();
                tagModel.setName(tag);
                tagsModel.add(tagModel);
            }
            manga.setTags(tagsModel);
        }

        return manga;
    }

//    public Manga transformMyAnimeListMangaToDAO(Manga myAnimeListManga){
//        Manga manga = new Manga();
//
//        ModelMapper modelMapper = new ModelMapper();
//        manga = modelMapper.map(myAnimeListManga, Manga.class);
//
//        String[] genres = myAnimeListManga.getGenres();
//        if (genres != null){
//            Set<Genre> genresModel = new HashSet<>();
//            for (String genre:genres){
//                Genre genreModel = new Genre();
//                genreModel.setName(genre);
//                genresModel.add(genreModel);
//            }
//            manga.setGenres(genresModel);
//        }
//
//        List<Character> characters = myAnimeListManga.getCharacters();
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
//            manga.setCharacters(charactersModel);
//        }
//
//        List<Author> authors = myAnimeListManga.getAuthors();
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
//            manga.setAuthors(authorsModel);
//        }
//
//        if (myAnimeListManga.getFinishedAiringDate() != null){
//            try {
//                SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
//                Date date = formatter.parse(myAnimeListManga.getFinishedAiringDate());
//                LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
//                manga.setFinishedAiringDate(dateTime.toString());
//            } catch (ParseException e) {
//                manga.setFinishedAiringDate(null);
//            }
//        }
//
//        if (myAnimeListManga.getStartedAiringDate() != null){
//            try {
//                SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
//                Date date = formatter.parse(myAnimeListManga.getStartedAiringDate());
//                LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
//                manga.setStartedAiringDate(dateTime.toString());
//            } catch (ParseException e) {
//                manga.setStartedAiringDate(null);
//            }
//        }
//
//        String[] synonyms = myAnimeListManga.getSynonyms();
//        if (synonyms != null) {
//            Synonym synonymModel;
//            Set<Synonym> synonymsModel = new HashSet<>();
//            for (String synonym : synonyms) {
//                synonymModel = new Synonym();
//                synonymModel.setTitle(synonym);
//                synonymModel.setEntry(manga);
//                synonymsModel.add(synonymModel);
//            }
//            manga.setSynonyms(synonymsModel);
//        }
//
//        String[] tags = myAnimeListManga.getTags();
//        if (tags != null) {
//            Tag tagModel;
//            Set<Tag> tagsModel = new HashSet<>();
//            for (String tag : tags) {
//                tagModel = new Tag();
//                tagModel.setName(tag);
//                tagsModel.add(tagModel);
//            }
//            manga.setTags(tagsModel);
//        }
//
//        return manga;
//    }
}
