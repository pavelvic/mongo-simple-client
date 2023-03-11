package config;

import com.google.inject.AbstractModule;

public class TestConfigInjector extends AbstractModule {

    @Override
    protected void configure() {
        bind(IMongoConfig.class).toProvider(MongoConfigProvider.class);
    }
}
