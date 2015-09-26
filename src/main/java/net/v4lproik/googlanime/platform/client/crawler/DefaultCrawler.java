package net.v4lproik.googlanime.platform.client.crawler;

import net.v4lproik.googlanime.platform.service.api.entities.Entry;
import net.v4lproik.googlanime.platform.service.api.models.SourceEnum;
import net.v4lproik.googlanime.platform.service.api.models.TypeEnum;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
public class DefaultCrawler implements Crawler{

    static Logger log = Logger.getLogger(DefaultCrawler.class.getName());

    Map<SourceEnum, Map<TypeEnum, AbstractUnitCrawler>> crawlers;

    public DefaultCrawler() {
        crawlers = new HashMap<SourceEnum, Map<TypeEnum, AbstractUnitCrawler>>() {};
    }

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
            throw new IllegalArgumentException(String.format("The crawler %s %s has not been registered", source.toString(), type.toString()));
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

        Map<TypeEnum, AbstractUnitCrawler> sourceCrawlers = crawlers.get(source);

        if (sourceCrawlers == null){
            Map<TypeEnum, AbstractUnitCrawler> tmp = new HashMap<>();
            tmp.put(type, unitCrawler);
            crawlers.put(source, tmp);
        }else{
            sourceCrawlers.put(type, unitCrawler);
        }

        log.debug(String.format("[DefaultCrawler] UnitCrawler type %s and source %s has been registered", type.toString(), source.toString()));
    }
}

