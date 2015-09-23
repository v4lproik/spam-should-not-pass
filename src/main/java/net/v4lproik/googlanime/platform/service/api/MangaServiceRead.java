package net.v4lproik.googlanime.platform.service.api;

import net.v4lproik.googlanime.platform.service.api.entities.Anime;

import java.util.List;

public interface MangaServiceRead {
    List<Anime> find(String query, String[] type, String[] fields);
    List<?> find(String query, String[] type, String[] fields, Class<?> toCast) throws IllegalAccessException, InstantiationException;
}
