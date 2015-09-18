package net.v4lproik.googlanime.client.crawler;

import net.v4lproik.googlanime.service.api.entities.Entry;
import net.v4lproik.googlanime.service.api.models.SourceEnum;
import net.v4lproik.googlanime.service.api.models.TypeEnum;
import org.springframework.context.annotation.Configuration;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
public class CrawlerRegistry implements Crawler{

    Map<SourceEnum, Map<TypeEnum, AbstractUnitCrawler>> crawlers;

    @Override
    public Entry crawl(Integer id, TypeEnum type) {
        throw new NotImplementedException();
    }

    @Override
    public Entry crawl(Integer id, TypeEnum type, SourceEnum source) throws IOException {
        Map<TypeEnum, AbstractUnitCrawler> sourceCrawlers = crawlers.get(source);
        if (sourceCrawlers == null){
            throw new IllegalArgumentException(String.format("The crawler %s has not been registered", source.toString()));
        }

        AbstractUnitCrawler crawler = sourceCrawlers.get(type);
        if (crawler == null){
            throw new IllegalArgumentException(String.format("The crawler %s has not been registered", source.toString()));
        }

        return crawler.crawl(id);
    }

    @Override
    public Set<Entry> crawl(Integer id, TypeEnum type, Boolean dependency) throws IOException {
        throw new NotImplementedException();
    }

    @Override
    public Set<Entry> crawl(Integer id, TypeEnum type, SourceEnum source, Boolean dependency) throws IOException {
        Map<TypeEnum, AbstractUnitCrawler> sourceCrawlers = crawlers.get(source);
        if (sourceCrawlers == null){
            throw new IllegalArgumentException(String.format("The crawler %s has not been registered", source.toString()));
        }

        AbstractUnitCrawler crawler = sourceCrawlers.get(type);
        if (crawler == null){
            throw new IllegalArgumentException(String.format("The crawler %s has not been registered", source.toString()));
        }

        return crawler.crawl(id, dependency);
    }

    @Override
    public void register(AbstractUnitCrawler unitCrawler, SourceEnum source, TypeEnum type) {

        Map<TypeEnum, AbstractUnitCrawler> tmp = new HashMap<>();
        tmp.put(type, unitCrawler);

        crawlers.put(source, tmp);
    }
}
