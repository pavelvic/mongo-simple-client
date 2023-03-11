package config;

import com.google.common.collect.Maps;
import com.google.inject.Provider;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

public class MongoConfigProvider implements Provider<IMongoConfig> {

    @Override
    public IMongoConfig get() {
        return ConfigFactory.create(IMongoConfig.class, this.getProperties());
    }

    @SneakyThrows
    private Map<String, String> getProperties() {
        Properties prop = new Properties();
        prop.load(Files.newInputStream(Paths.get(IMongoConfig.class.getAnnotation(FileName.class).value())));
        return Maps.fromProperties(prop);
    }
}