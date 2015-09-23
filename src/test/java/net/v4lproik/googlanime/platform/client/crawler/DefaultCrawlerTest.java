package net.v4lproik.googlanime.platform.client.crawler;

import com.github.v4lproik.myanimelist.api.impl.AnimeCrawler;
import com.github.v4lproik.myanimelist.api.models.Anime;
import net.v4lproik.googlanime.platform.client.crawler.mal.MALUnitCrawler;
import net.v4lproik.googlanime.platform.service.api.entities.Entry;
import net.v4lproik.googlanime.platform.service.api.models.SourceEnum;
import net.v4lproik.googlanime.platform.service.api.models.TypeEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCrawlerTest {

    Crawler crawlerRegistry;

    @Mock
    AnimeCrawler animeCrawler;

    @Mock
    MALUnitCrawler malUnitCrawler;

    @Before
    public void setUp(){
        crawlerRegistry = new DefaultCrawler();
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_crawlEntity_withNoCralwerRegistered_shouldReturnNOK() throws IOException {
        crawlerRegistry.crawl(0, TypeEnum.ANIME, SourceEnum.MAL);
    }

    public void test_crawlEntity_withCralwerRegistered_shouldReturnOK() throws IOException {
        // Then
        crawlerRegistry.register(new MALUnitCrawler(animeCrawler), SourceEnum.MAL, TypeEnum.ANIME);

        when(animeCrawler.crawl(20)).thenReturn(new Anime(20));

        // When
        Entry entry = crawlerRegistry.crawl(20, TypeEnum.ANIME, SourceEnum.MAL);

        // Given
        assertEquals(entry.getId(), new Long(20));
    }
}