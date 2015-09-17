package net.v4lproik.googlanime.client.crawler;

import com.github.v4lproik.myanimelist.api.impl.AnimeCrawler;
import com.github.v4lproik.myanimelist.api.impl.MangaCrawler;
import net.v4lproik.googlanime.client.crawler.mal.MALUnitCrawler;
import net.v4lproik.googlanime.service.api.models.SourceEnum;
import net.v4lproik.googlanime.service.api.models.TypeEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigCrawler {

    private Crawler initializeCrawler(){
        Crawler crawlerRegistry = new CrawlerRegistry();

        //register crawlers
        crawlerRegistry.register(new MALUnitCrawler(new AnimeCrawler()), SourceEnum.MAL, TypeEnum.ANIME);
        crawlerRegistry.register(new MALUnitCrawler(new MangaCrawler()), SourceEnum.MAL, TypeEnum.MANGA);

        return crawlerRegistry;
    }

    @Bean
    public Crawler crawlerRegistry(){
        return initializeCrawler();
    }
}
