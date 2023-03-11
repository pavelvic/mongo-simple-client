package client;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.result.UpdateResult;
import config.MongoConfig;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Mongo<T extends MongoDocument> implements AutoCloseable {

    private final ConnectionString connection;
    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<T> collection;

    public Mongo(Class<T> clazz) {
        this(MongoConfig.getMongoConfig().getDefaultCollection(), clazz);
    }

    public Mongo(String collection, Class<T> clazz) {
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        connection = new ConnectionString(MongoConfig.getMongoConfig().getConnectionString());
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connection)
                .codecRegistry(codecRegistry)
                .build();
        mongoClient = MongoClients.create(clientSettings);
        database = mongoClient.getDatabase(MongoConfig.getMongoConfig().getDefaultDatabase());
        this.collection = database.getCollection(collection, clazz);
    }

    public List<T> executeSelect(MongoSelectQuery query) {
        List<T> result = new ArrayList<>();
        FindIterable<T> find;
        if (Objects.isNull(query.getFilter())) {
            find = collection.find();
        } else {
            find = collection.find(query.getFilter());
        }
        if (Objects.nonNull(query.getProjection())) find.projection(query.getProjection());
        if (Objects.nonNull(query.getSort())) find.sort(query.getSort());
        if (Objects.nonNull(query.getLimit())) find.limit(query.getLimit());
        if (Objects.nonNull(query.getSkip())) find.skip(query.getSkip());
        find.into(result);
        return result;
    }

    public UpdateResult executeUpdateDoc(T doc) {
        Document updateObject = new Document().append("$set", doc);
        return collection.updateOne(eq("_id", doc.getId()), updateObject);
    }

    public UpdateResult executeUpdateDoc(T source, T dest) {
        Document updateObject = new Document().append("$set", dest);
        return collection.updateOne(source.toDocument(), updateObject);
    }

    public UpdateResult executeUpdateOne(MongoUpdateQuery query) {
        return collection.updateOne(query.getFilter(), query.getUpdates());
    }

    public UpdateResult executeUpdateMany(MongoUpdateQuery query) {
        return collection.updateMany(query.getFilter(), query.getUpdates());
    }

    @Override
    public void close() throws Exception {
        mongoClient.close();
    }
}
