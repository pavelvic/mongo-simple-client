package documents;

import client.MongoDocument;
import lombok.Data;
import org.bson.Document;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import java.time.LocalDateTime;

@Data
public class RapnetDocument implements MongoDocument {
    private ObjectId id;
    @BsonProperty(value = "createDate")
    private LocalDateTime createDate;

    @Override
    public Document toDocument() {
        return new Document()
                .append("_id", id)
                .append("createDate", createDate);
    }
}