package net.v4lproik.googlanime.service.api;

import net.v4lproik.googlanime.service.api.entities.AnimeModel;

import java.util.List;

public interface AnimeServiceRead {
    List<AnimeModel> find(String query, String[] type, String[] fields);
    List<?> find(String query, String[] type, String[] fields, Class<?> toCast) throws IllegalAccessException, InstantiationException;
}