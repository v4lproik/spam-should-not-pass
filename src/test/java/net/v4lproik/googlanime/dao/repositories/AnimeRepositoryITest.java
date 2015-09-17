package net.v4lproik.googlanime.dao.repositories;

import net.v4lproik.googlanime.client.mysql.DatabaseTestConfiguration;
import net.v4lproik.googlanime.client.mysql.SqlDatabaseInitializer;
import net.v4lproik.googlanime.dao.api.AnimeDao;
import net.v4lproik.googlanime.service.api.entities.AnimeModel;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {
                DatabaseTestConfiguration.class,
        })
@ImportResource("classpath*  : application-context.xml")
@WebAppConfiguration
@TransactionConfiguration
public class AnimeRepositoryITest {

    @Autowired
    SqlDatabaseInitializer databaseInitializer;

    @Autowired
    SessionFactory sessionFactoryConfig;

    AnimeDao animeDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        try {
            databaseInitializer.createDatabase();
        }catch (Exception e){
            // Something went wrong while importing the database schema
        }

        animeDao = new AnimeRepository(sessionFactoryConfig);
    }

    @Test
    public void test_saveAnime_shouldBeInserted() throws Exception {

        // Given
        AnimeModel response = getAnime();

        // When
        animeDao.saveOrUpdate(response);

        // Then
    }

    @Test
    public void test_saveAnimeTwice_shouldBeUpdated() throws Exception {

        // Given
        AnimeModel animeRes = getAnime();

        // When
        animeDao.saveOrUpdate(animeRes);
        animeRes.setTitle(animeRes.getTitle() + "_UPDATED");
        animeDao.saveOrUpdate(animeRes);

        AnimeModel animeFind = animeDao.findById(animeRes.getId());

        String title = animeRes.getTitle();

        // Then
        assertEquals(title, animeFind.getTitle());

    }

    @Test
    public void deleteAnime_shouldBeOK() throws Exception{

        // Given
        AnimeModel response = getAnime();

        animeDao.saveOrUpdate(response);

        animeDao.deleteById(response.getId());
    }

    private AnimeModel getAnime() throws IOException{
        // Given
        AnimeModel anime = new AnimeModel();
        anime.setId(new Long(1));
        anime.setTitle("Test Title");
        anime.setType("anime");

        return anime;
    }
}