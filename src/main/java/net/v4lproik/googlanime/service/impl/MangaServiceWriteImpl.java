package net.v4lproik.googlanime.service.impl;

import net.v4lproik.googlanime.dao.api.MangaDao;
import net.v4lproik.googlanime.service.api.MangaServiceWrite;
import net.v4lproik.googlanime.service.api.entities.AnimeIdModel;
import net.v4lproik.googlanime.service.api.entities.MangaModel;
import net.v4lproik.googlanime.service.api.utils.TransformMangaMapper;
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
    public void save(MangaModel mangaRes) {
//        MangaModel mangaRes = mangaMapper.transformMyAnimeListMangaDependencyToDAO(manga);

        if (mangaRes.getTitle() != null) {

            if (mangaRes.getId() != null){

                if (mangaDao.findById(mangaRes.getId()) == null){
                    AnimeIdModel entryId = mangaMapper.transformEntryToEntryId(mangaRes);
                    mangaDao.save(entryId);
                }

                mangaDao.saveOrUpdate(mangaRes);
            }
        }
    }

    @Transactional( readOnly = false)
    @Override
    public void save(AnimeIdModel entity) {
//        MangaModel entity = mangaMapper.transformMyAnimeListMangaToDAO(manga);
//        log.debug(entity.toString());
        mangaDao.save(entity);
    }

    @Transactional( readOnly = false)
    @Override
    public void delete(MangaModel entity) {
//        MangaModel entity = mangaMapper.transformMyAnimeListMangaToDAO(manga);
        mangaDao.delete(entity);
    }

    private boolean isSavable(MangaModel anime){
        return anime.getTitle() != null ? true : false;
    }
}
