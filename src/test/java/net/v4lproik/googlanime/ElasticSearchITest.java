package net.v4lproik.googlanime;

import net.v4lproik.googlanime.client.elasticsearch.ElasticsearchIndexInitializer;
import net.v4lproik.googlanime.client.elasticsearch.ElasticsearchTestConfiguration;
import org.elasticsearch.client.Client;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {
                ElasticsearchTestConfiguration.class
        })
@ImportResource("classpath*  : application-context.xml")
public class ElasticSearchITest {

    @Autowired
    private ElasticsearchIndexInitializer elasticsearchIndexInitializer;

    @Autowired
    private Client client;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        try {
            elasticsearchIndexInitializer.createIndex();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    @Test
    public void elasticsearch_getInformation_fromAnimesIndex_shouldBeOk(){
        try {
            client.admin ().cluster ().prepareHealth("animes").setWaitForGreenStatus().execute().actionGet();

            client.prepareGet("animes", "anime", "1")
                    .execute()
                    .actionGet();
        }catch (Exception e){
            fail();
        }

    }

    @Test
    public void elasticsearch_getInformation_fromMangasIndex_shouldBeOk(){
        try {
            client.admin ().cluster ().prepareHealth("mangas").setWaitForGreenStatus().execute().actionGet();

            client.prepareGet("mangas", "manga", "2")
                    .execute()
                    .actionGet();
        }catch (Exception e){
            fail();
        }

    }
}