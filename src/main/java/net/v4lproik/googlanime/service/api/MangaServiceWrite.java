package net.v4lproik.googlanime.service.api;

import net.v4lproik.googlanime.service.api.entities.AnimeIdModel;
import net.v4lproik.googlanime.service.api.entities.MangaModel;

public interface MangaServiceWrite {
    void save(AnimeIdModel manga);

    void save(MangaModel manga);

    void delete(MangaModel manga);
}
