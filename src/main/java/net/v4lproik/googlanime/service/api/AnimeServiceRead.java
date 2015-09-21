package net.v4lproik.googlanime.service.api;

import net.v4lproik.googlanime.service.api.entities.Anime;

import java.util.List;

public interface AnimeServiceRead {
    List<Anime> find(String query, String[] type, String[] fields);
    List<?> find(String query, String[] type, String[] fields, Class<?> toCast) throws IllegalAccessException, InstantiationException;
}