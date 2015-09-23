package net.v4lproik.googlanime.platform.dao.api;

import net.v4lproik.googlanime.platform.service.api.entities.AnimeId;

public interface EntryDao {
    Long save(AnimeId anime);
}
