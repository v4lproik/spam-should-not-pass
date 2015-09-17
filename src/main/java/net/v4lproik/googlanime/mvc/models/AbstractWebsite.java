package net.v4lproik.googlanime.mvc.models;

import net.v4lproik.googlanime.service.api.entities.Entry;

import java.io.IOException;
import java.util.Set;

public abstract class AbstractWebsite {
    /**
     * This functions aims to crawl an anime through the website
     * @param opts contains the type manga or anime and the query's name
     * @return a MyAnimeListAnimes. Its one object that contains all the depedencies
     * @throws IOException
     */
    public abstract Entry crawl(String name, String type) throws IOException;

    /**
     * This functions aims to crawl an anime through the website by id
     * @param opts contains the type manga or anime and the query's name
     * @return
     * @throws IOException
     */
    public abstract Entry crawl(Integer id, String type) throws IOException;

    /**
     * This functions aims to crawl an anime through the website by id
     * @param opts contains the type manga or anime and the query's name
     * @return
     * @throws IOException
     */
    public abstract Set<Entry> crawlAndDependencies(Integer id, String type) throws IOException;

    /**
     * This function aims to call an API
     */
    public abstract void call();

}
