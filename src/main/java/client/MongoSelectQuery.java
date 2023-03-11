package client;

import lombok.Builder;
import lombok.Getter;
import org.bson.conversions.Bson;

@Builder
@Getter
public class MongoSelectQuery {
    private final Bson filter;
    private final Bson projection;
    private final Bson sort;
    private final Integer limit;
    private final Integer skip;
}
