import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class ElasticsearchExample {

    public static void main(String[] args) throws IOException {

        // Configura as conexões de cliente
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "http")).build();
        RestHighLevelClient client = new RestHighLevelClient(restClient);

        // Cria um índice
        CreateIndexRequest request = new CreateIndexRequest("meu_indice");
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2));
        request.mapping("{\n" +
                "  \"properties\": {\n" +
                "    \"title\": {\n" +
                "      \"type\": \"text\"\n" +
                "    },\n" +
                "    \"description\": {\n" +
                "      \"type\": \"text\"\n" +
                "    }\n" +
                "  }\n" +
                "}", XContentType.JSON);
        CreateIndexResponse createIndexResponse = client.indices().create(request);
        boolean acknowledged = createIndexResponse.isAcknowledged();
        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();

        // Fecha as conexões de cliente
        restClient.close();
        client.close();
    }
}
