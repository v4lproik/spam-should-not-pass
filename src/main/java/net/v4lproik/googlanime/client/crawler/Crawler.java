package net.v4lproik.googlanime.client.crawler;

import net.v4lproik.googlanime.service.api.entities.Entry;
import net.v4lproik.googlanime.service.api.models.SourceEnum;
import net.v4lproik.googlanime.service.api.models.TypeEnum;

import java.io.IOException;
import java.util.Set;

public interface Crawler {
    Entry crawl(Integer id, TypeEnum type) throws IOException;
    Entry crawl(Integer id, TypeEnum type, SourceEnum source) throws IOException;

    Set<Entry> crawl(Integer id, TypeEnum type, Boolean dependency) throws IOException;
    Set<Entry> crawl(Integer id, TypeEnum type, SourceEnum source, Boolean dependency) throws IOException;

    void register(AbstractUnitCrawler unitCrawler, SourceEnum source, TypeEnum type);
}
