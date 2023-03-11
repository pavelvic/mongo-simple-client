package client;

import com.mongodb.client.model.UpdateOptions;
import lombok.Builder;
import lombok.Getter;
import org.bson.conversions.Bson;

@Builder
@Getter
public class MongoUpdateQuery {
    private final Bson filter;
    private final Bson updates;
    private final UpdateOptions options;
}
