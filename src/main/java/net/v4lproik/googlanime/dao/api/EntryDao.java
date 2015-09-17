package net.v4lproik.googlanime.dao.api;

import net.v4lproik.googlanime.service.api.entities.AnimeIdModel;

public interface EntryDao {
    Long save(AnimeIdModel anime);
}
