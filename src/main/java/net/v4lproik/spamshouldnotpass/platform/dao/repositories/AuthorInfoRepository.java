package net.v4lproik.spamshouldnotpass.platform.dao.repositories;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import net.v4lproik.spamshouldnotpass.platform.client.dynamodb.AuthorMessageTableInitializer;
import net.v4lproik.spamshouldnotpass.platform.models.entities.AuthorInfo;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Repository
public class AuthorInfoRepository {

    private static final Logger log = LoggerFactory.getLogger(AuthorInfoRepository.class);

    private final DynamoDB dynamoDB;
    private final ObjectMapper objectMapper;

    public final String tableName = AuthorMessageTableInitializer.TABLE_NAME;

    @Autowired
    public AuthorInfoRepository(final DynamoDB dynamoDB, final ObjectMapper objectMapper) {
        this.dynamoDB = checkNotNull(dynamoDB);
        this.objectMapper = checkNotNull(objectMapper);
    }

    public void store(AuthorInfo authorInfo){
        checkNotNull(authorInfo);
        checkNotNull(authorInfo.getAuthorId());
//        checkNotNull(authorInfo.getMessages());
        checkNotNull(authorInfo.getNumberOfDocumentsSubmittedInTheLast5min());

        final Table table = dynamoDB.getTable(tableName);

        try {
            Item item = new Item()
                    .withPrimaryKey(AuthorMessageTableInitializer.HASH_NAME, authorInfo.getAuthorId())
                    .withString(AuthorMessageTableInitializer.RANGE_NAME, String.format("%s_%s", DateTime.now().toDateTime(DateTimeZone.UTC), authorInfo.getCorporation()))
                    .withJSON(AuthorMessageTableInitializer.INFO_NAME, this.objectMapper.writeValueAsString(authorInfo));

            table.putItem(item);
        } catch (JsonProcessingException e) {
            log.error("Unable to serialize the properties for author info.", e);
        }
    }

    public Optional<AuthorInfo> get(String authorId) {
        checkNotNull(authorId);

        final Table table = dynamoDB.getTable(tableName);

        QuerySpec query = new QuerySpec()
                .withHashKey(AuthorMessageTableInitializer.HASH_NAME, authorId);

        ItemCollection<QueryOutcome> items = table.query(query);

        Iterator<AuthorInfo> transform = parseResult(items);

        if (transform.hasNext()){
            return Optional.of(transform.next());
        }

        return Optional.empty();
    }

    public void delete(String authorId) {
        checkNotNull(authorId);

        final Table table = dynamoDB.getTable(tableName);

        table.deleteItem(AuthorMessageTableInitializer.HASH_NAME, authorId);
    }


    public Integer getNumberOfDocumentsSubmittedInTheLast5min(String authorId, String corporation) {
        checkNotNull(authorId);

        final Table table = dynamoDB.getTable(tableName);

        RangeKeyCondition rangeKeyCondition = new RangeKeyCondition(AuthorMessageTableInitializer.RANGE_NAME);
        rangeKeyCondition = rangeKeyCondition.ge(DateTime.now().minusMinutes(5).toDateTime(DateTimeZone.UTC).toString());

        QuerySpec query = new QuerySpec()
                .withHashKey(AuthorMessageTableInitializer.HASH_NAME, authorId)
                .withRangeKeyCondition(rangeKeyCondition);


        ItemCollection<QueryOutcome> items = table.query(query);

        return Iterators.size(parseResult(items));
    }

    private Iterator<AuthorInfo> parseResult(ItemCollection<QueryOutcome> items){

        return Iterators.transform(items.iterator(), new Function<Item, AuthorInfo>() {

            @Nullable
            @Override
            public AuthorInfo apply(Item item) {
                AuthorInfo result = null;
                try {
                    result = objectMapper.readValue(item.getJSON(AuthorMessageTableInitializer.INFO_NAME), AuthorInfo.class);
                } catch (IOException e) {
                    log.error("Unable to deserialize the properties for author info.", e);
                }
                return result;
            }
        });
    }
}
