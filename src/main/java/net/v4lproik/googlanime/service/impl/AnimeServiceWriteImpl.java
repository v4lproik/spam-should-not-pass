package net.v4lproik.googlanime.service.impl;

import net.v4lproik.googlanime.dao.api.AnimeDao;
import net.v4lproik.googlanime.service.api.AnimeServiceWrite;
import net.v4lproik.googlanime.service.api.entities.Anime;
import net.v4lproik.googlanime.service.api.entities.AnimeId;
import net.v4lproik.googlanime.service.api.utils.TransformAnimeMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnimeServiceWriteImpl implements AnimeServiceWrite {

    static Logger log = Logger.getLogger(AnimeServiceWriteImpl.class.getName());

    public final AnimeDao animeDao;

    public final TransformAnimeMapper animeMapper;

    @Autowired
    public AnimeServiceWriteImpl(final AnimeDao animeDao, final TransformAnimeMapper animeMapper) {
        this.animeDao = animeDao;
        this.animeMapper = animeMapper;
    }

    @Transactional( readOnly = false)
    @Override
    public void save(Anime animeRes) {
//        Anime animeRes = animeMapper.transformMyAnimeListAnimeDependencyToDAO(anime);

        if (isSavable(animeRes)) {

            if (animeRes.getId() != null){

                if (animeDao.findById(animeRes.getId()) == null){
                    AnimeId entryId = animeMapper.transformEntryToEntryId(animeRes);
                    animeDao.save(entryId);
                }

                animeDao.saveOrUpdate(animeRes);
            }else{
                //get an Id first

            }
        }
    }

    @Transactional( readOnly = false)
    @Override
    public void save(AnimeId entity) {
//        Anime entity = animeMapper.transformMyAnimeListAnimeToDAO(anime);
        log.debug(entity.toString());
        animeDao.save(entity);
    }

    @Transactional( readOnly = false)
    @Override
    public void delete(Anime anime) {
//        Anime entity = animeMapper.transformMyAnimeListAnimeToDAO(anime);
        animeDao.delete(anime);
    }

    private boolean isSavable(Anime anime){
        return anime.getTitle() != null ? true : false;
    }
}
