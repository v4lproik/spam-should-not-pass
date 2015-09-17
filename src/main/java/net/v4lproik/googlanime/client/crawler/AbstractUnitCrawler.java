package net.v4lproik.googlanime.client.crawler;

import net.v4lproik.googlanime.service.api.entities.Entry;

import java.io.IOException;
import java.util.Set;

public abstract class AbstractUnitCrawler {
    public abstract Entry crawl(Integer id) throws IOException;

    public abstract Set<Entry> crawl(Integer id, Boolean dependency) throws IOException;}
