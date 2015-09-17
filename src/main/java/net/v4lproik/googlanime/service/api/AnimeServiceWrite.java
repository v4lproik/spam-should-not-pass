package net.v4lproik.googlanime.service.api;

import net.v4lproik.googlanime.service.api.entities.AnimeIdModel;
import net.v4lproik.googlanime.service.api.entities.AnimeModel;

public interface AnimeServiceWrite {
    void save(AnimeModel anime);

    void save(AnimeIdModel anime);

    void delete(AnimeModel anime);
}
