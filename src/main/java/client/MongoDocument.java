package client;

import org.bson.Document;
import org.bson.types.ObjectId;

public interface MongoDocument {
    ObjectId getId();

    Document toDocument();
}