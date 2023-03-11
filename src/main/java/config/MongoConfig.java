package config;

import com.google.inject.Guice;
import lombok.SneakyThrows;

public class MongoConfig {

    private static IMongoConfig mongoConfig;

    @SneakyThrows
    public static IMongoConfig getMongoConfig() {
        if (mongoConfig == null)
            mongoConfig = Guice.createInjector(TestConfigInjector.class.getDeclaredConstructor().newInstance()).getInstance(IMongoConfig.class);
        return mongoConfig;
    }
}
