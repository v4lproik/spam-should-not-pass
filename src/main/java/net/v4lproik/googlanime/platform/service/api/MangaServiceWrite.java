package net.v4lproik.googlanime.platform.service.api;

import net.v4lproik.googlanime.platform.service.api.entities.AnimeId;
import net.v4lproik.googlanime.platform.service.api.entities.Manga;

public interface MangaServiceWrite {
    void save(AnimeId manga);

    void save(Manga manga);

    void delete(Manga manga);
}
