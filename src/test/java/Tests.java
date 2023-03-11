import client.Mongo;
import client.MongoSelectQuery;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.UpdateResult;
import documents.RapnetDocument;
import lombok.SneakyThrows;
import org.bson.conversions.Bson;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Tests {

    @SneakyThrows
    @Test
    public void findAndUpdateMongo() {
        try (Mongo<RapnetDocument> mongo = new Mongo<>(RapnetDocument.class)) {
            //find source
            Bson projectionFields = Projections.fields(
                    Projections.include("createDate"));
            Bson sort = Sorts.descending("createDate");
            MongoSelectQuery queryLastDoc = MongoSelectQuery.builder()
                    .projection(projectionFields)
                    .sort(sort)
                    .limit(1)
                    .build();
            List<RapnetDocument> resultList = mongo.executeSelect(queryLastDoc);
            RapnetDocument expected = resultList.get(0);

            //update source
            LocalDateTime date = LocalDateTime.now();
            expected.setCreateDate(date);
            UpdateResult updateResult = mongo.executeUpdateDoc(expected);
            System.out.println("Update Result: " + updateResult);

            //assert changes
            MongoSelectQuery queryExpectedDoc = MongoSelectQuery.builder()
                    .filter(eq("_id", expected.getId()))
                    .build();
            RapnetDocument actual = mongo.executeSelect(queryExpectedDoc).get(0);
            assertThat("expected is actual", expected, is(actual));
        }
    }
}