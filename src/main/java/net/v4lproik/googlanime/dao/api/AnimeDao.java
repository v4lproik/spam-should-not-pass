package net.v4lproik.googlanime.dao.api;

import net.v4lproik.googlanime.service.api.entities.AnimeModel;

public interface AnimeDao extends EntryDao{
    Long save(AnimeModel anime);
    AnimeModel findById(Long id);
    AnimeModel find(AnimeModel anime);
    void saveOrUpdate(AnimeModel anime);
    void update(AnimeModel anime);
    void delete(AnimeModel anime);
    void deleteById(Long id);
}
