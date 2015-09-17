package net.v4lproik.googlanime.dao.api;

import net.v4lproik.googlanime.service.api.entities.MangaModel;

public interface MangaDao extends EntryDao{
    Long save(MangaModel manga);
    MangaModel findById(Long id);
    MangaModel find(MangaModel anime);
    void saveOrUpdate(MangaModel manga);
    void delete(MangaModel manga);
    void deleteById(Long id);
}
