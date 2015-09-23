package net.v4lproik.googlanime.platform.dao.api;

import net.v4lproik.googlanime.platform.service.api.entities.Manga;

public interface MangaDao extends EntryDao{
    Long save(Manga manga);
    Manga findById(Long id);
    Manga find(Manga anime);
    void saveOrUpdate(Manga manga);
    void delete(Manga manga);
    void deleteById(Long id);
}
