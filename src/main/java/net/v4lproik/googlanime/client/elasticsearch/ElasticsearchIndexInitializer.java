package net.v4lproik.googlanime.client.elasticsearch;

import org.apache.commons.io.IOUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ElasticsearchIndexInitializer {

    public final static String CONF_ELASTICSEARCH_BASE_FOLDER = "elasticsearch/mapping/";
    @NotNull
    private Client client;

    public ElasticsearchIndexInitializer(Client client) {
        this.client = client;
    }

    public void createIndex() throws Exception {

        Map<String, List<String>> mapIndexesIndices = getIndexIndices();

        if (mapIndexesIndices.size() > 0){
            Iterator it = mapIndexesIndices.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                String index = pair.getKey().toString();
                List<String> indices = (List<String>) pair.getValue();

                // Always remove the indexes and indices already in elasticache
                removeIndex(index);

                for (String indice : indices){
                    CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate(index).addMapping(indice, getIndexFieldMapping(index, indice));
                    cirb.execute().actionGet();

                    // Fix CurrentState[POST_RECOVERY] operations only allowed when started/relocated Exception
                    waitES();
                    refreshES(index);
                }
            }
        }
    }

    private Map<String, List<String>> getIndexIndices(){
        Map<String, List<String>> mapIndexesIndices = new HashMap<String, List<String>>();

        File node = new File(getClass().getClassLoader().getResource(CONF_ELASTICSEARCH_BASE_FOLDER).getFile());

        if(node.isDirectory()){
            String[] indexes = node.list();
            for(String index : indexes){
                File subNode = new File(getClass().getClassLoader().getResource(CONF_ELASTICSEARCH_BASE_FOLDER + "/" + index).getFile());
                List<String> indices = new ArrayList<>();
                for (File indice : subNode.listFiles()){
                    try{
                        indices.add(indice.getName().split("\\.")[0]);
                    }catch(Exception e){
                        // Avoid hidden file or whatsoever
                    }
                }
                mapIndexesIndices.put(index, indices);
            }
        }

        return mapIndexesIndices;
    }

    public void removeIndex(String index) throws Exception {
        try {
            client.admin().indices().prepareDelete(index).get();
        } catch (Exception e) {
            // nothing
        }
    }

    private String getIndexFieldMapping(String index, String indice) throws IOException {
        return IOUtils.toString(getClass().getClassLoader().getResourceAsStream(CONF_ELASTICSEARCH_BASE_FOLDER + index + "/" + indice + ".json"));
    }

    private void waitES() {
        client.admin().cluster().prepareHealth().setWaitForYellowStatus().setTimeout(TimeValue.timeValueSeconds(1)).get();
    }

    private void refreshES(String index) {
        client.admin().indices().prepareRefresh(index).get();
    }
}
