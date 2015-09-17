package net.v4lproik.googlanime.client.crawler.mal;

import com.github.v4lproik.myanimelist.api.UnitCrawler;
import com.github.v4lproik.myanimelist.api.impl.AnimeCrawler;
import com.github.v4lproik.myanimelist.api.impl.MangaCrawler;
import com.github.v4lproik.myanimelist.api.models.Anime;
import com.github.v4lproik.myanimelist.api.models.Item;
import com.github.v4lproik.myanimelist.api.models.Manga;
import net.v4lproik.googlanime.client.crawler.AbstractUnitCrawler;
import net.v4lproik.googlanime.service.api.entities.Entry;
import net.v4lproik.googlanime.service.api.models.TypeEnum;
import net.v4lproik.googlanime.service.api.utils.TransformAnimeMapper;
import net.v4lproik.googlanime.service.api.utils.TransformMangaMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MALUnitCrawler extends AbstractUnitCrawler {

    private final UnitCrawler unitCrawler;

    private final TypeEnum type;

    @Autowired
    TransformAnimeMapper animeMapper;

    @Autowired
    TransformMangaMapper mangaMapper;

    public MALUnitCrawler(UnitCrawler unitCrawler) {
        this.unitCrawler = unitCrawler;

        if (unitCrawler instanceof AnimeCrawler){
            this.type = TypeEnum.ANIME;
        }else if (unitCrawler instanceof MangaCrawler){
            this.type = TypeEnum.MANGA;
        }else {
            this.type = null;
        }
    }

    @Override
    public Entry crawl(Integer id) throws IOException {

        Item malItem = this.unitCrawler.crawl(id);

        Entry transformedEntry = null;

        switch (type){
            case ANIME:
                transformedEntry = animeMapper.transformMyAnimeListAnimeDependencyToDAO((Anime) malItem);
                break;

            case MANGA:
                transformedEntry = mangaMapper.transformMyAnimeListMangaDependencyToDAO((Manga) malItem);
                break;
        }

        return transformedEntry;
    }

    @Override
    public Set<Entry> crawl(Integer id, Boolean dependency) throws IOException {
        Set<Item> malItems = this.unitCrawler.crawl(id, dependency);

        Set<Entry> transformedEntries = new HashSet<>();

        for (Item item:malItems){
            if (item instanceof Anime){
                transformedEntries.add(animeMapper.transformMyAnimeListAnimeDependencyToDAO((Anime) item));
            }

            if (item instanceof Manga){
                transformedEntries.add(mangaMapper.transformMyAnimeListMangaDependencyToDAO((Manga) item));
            }

            if (item instanceof com.github.v4lproik.myanimelist.api.models.Character){

            }
        }

        return transformedEntries;
    }
}
