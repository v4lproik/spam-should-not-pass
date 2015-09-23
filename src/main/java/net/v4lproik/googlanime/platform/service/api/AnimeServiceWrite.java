package net.v4lproik.googlanime.platform.service.api;

import net.v4lproik.googlanime.platform.service.api.entities.Anime;
import net.v4lproik.googlanime.platform.service.api.entities.AnimeId;

public interface AnimeServiceWrite {
    void save(Anime anime);

    void save(AnimeId anime);

    void delete(Anime anime);
}
