package net.v4lproik.googlanime.service.api;

import net.v4lproik.googlanime.service.api.entities.AnimeId;
import net.v4lproik.googlanime.service.api.entities.Manga;

public interface MangaServiceWrite {
    void save(AnimeId manga);

    void save(Manga manga);

    void delete(Manga manga);
}
