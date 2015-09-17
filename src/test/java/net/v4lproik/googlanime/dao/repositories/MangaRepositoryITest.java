package net.v4lproik.googlanime.dao.repositories;

import com.github.v4lproik.myanimelist.api.models.TypeEnum;
import net.v4lproik.googlanime.client.mysql.DatabaseTestConfiguration;
import net.v4lproik.googlanime.client.mysql.SqlDatabaseInitializer;
import net.v4lproik.googlanime.dao.api.MangaDao;
import net.v4lproik.googlanime.service.api.entities.MangaModel;
import net.v4lproik.googlanime.service.api.utils.TransformMangaMapper;
import org.hibernate.SessionFactory;
import org.junit.After;
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
public class MangaRepositoryITest {

    @Autowired
    SqlDatabaseInitializer databaseInitializer;

    @Autowired
    SessionFactory sessionFactoryConfig;

    MangaDao mangaDao;

    TransformMangaMapper mapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        try {
            databaseInitializer.createDatabase();
        }catch (Exception e){
            // Something went wrong while importing the database schema
        }
        mangaDao = new MangaRepository(sessionFactoryConfig);
    }

    @Test
    public void test_saveManga_shouldBeInserted() throws Exception {

        // Given
        MangaModel response = getManga();

        // When
        mangaDao.saveOrUpdate(response);
    }

    @Test
    public void test_saveMangaTwice_shouldBeUpdated() throws Exception {

        // Given
        MangaModel mangaRes = getManga();

        // When
        mangaDao.saveOrUpdate(mangaRes);
        mangaRes.setTitle(mangaRes.getTitle() + "_UPDATED");
        mangaDao.saveOrUpdate(mangaRes);

        MangaModel mangaFind = mangaDao.findById(mangaRes.getId());

        String title = mangaRes.getTitle();

        // Then
        assertEquals(title, mangaFind.getTitle());

    }

    @After
    public void rollBack() throws Exception{
        mangaDao.deleteById(new Long(11));
    }

    private MangaModel getManga() throws IOException {
        // Given
        TypeEnum type = TypeEnum.MANGA;
        Integer id = 11;

        MangaModel manga = new MangaModel();
        manga.setId(new Long(id));
        manga.setType(type.toString());

        return manga;
    }
}