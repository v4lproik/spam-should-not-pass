package net.v4lproik.googlanime.platform.service.impl;

import net.v4lproik.googlanime.platform.dao.api.MangaDao;
import net.v4lproik.googlanime.platform.service.api.MangaServiceWrite;
import net.v4lproik.googlanime.platform.service.api.entities.AnimeId;
import net.v4lproik.googlanime.platform.service.api.entities.Manga;
import net.v4lproik.googlanime.platform.service.api.utils.TransformMangaMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MangaServiceWriteImpl implements MangaServiceWrite {

    static Logger log = Logger.getLogger(MangaServiceWriteImpl.class.getName());

    public final MangaDao mangaDao;

    public final TransformMangaMapper mangaMapper;

    @Autowired
    public MangaServiceWriteImpl(final MangaDao mangaDao, final TransformMangaMapper mangaMapper) {
        this.mangaDao = mangaDao;
        this.mangaMapper = mangaMapper;
    }

    @Transactional( readOnly = false)
    @Override
    public void save(Manga mangaRes) {
//        Manga mangaRes = mangaMapper.transformMyAnimeListMangaDependencyToDAO(manga);

        if (mangaRes.getTitle() != null) {

            if (mangaRes.getId() != null){

                if (mangaDao.findById(mangaRes.getId()) == null){
                    AnimeId entryId = mangaMapper.transformEntryToEntryId(mangaRes);
                    mangaDao.save(entryId);
                }

                mangaDao.saveOrUpdate(mangaRes);
            }
        }
    }

    @Transactional( readOnly = false)
    @Override
    public void save(AnimeId entity) {
//        Manga entity = mangaMapper.transformMyAnimeListMangaToDAO(manga);
//        log.debug(entity.toString());
        mangaDao.save(entity);
    }

    @Transactional( readOnly = false)
    @Override
    public void delete(Manga entity) {
//        Manga entity = mangaMapper.transformMyAnimeListMangaToDAO(manga);
        mangaDao.delete(entity);
    }

    private boolean isSavable(Manga anime){
        return anime.getTitle() != null ? true : false;
    }
}
