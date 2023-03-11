package config;

import org.aeonbits.owner.Config;

@FileName("src/main/resources/mongo.properties")
public interface IMongoConfig extends Config {
    @Key("mongo.connection.string")
    String getConnectionString();

    @Key("mongo.defaultDatabase")
    String getDefaultDatabase();

    @Key("mongo.defaultCollection")
    String getDefaultCollection();
}