package net.v4lproik.googlanime.dao.api;

import net.v4lproik.googlanime.service.api.entities.AnimeId;

public interface EntryDao {
    Long save(AnimeId anime);
}
