package de.adesso.squadmap.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DatabaseConfig {

    @Value("${spring.data.neo4j.uri:default}")
    private String neoUri;

    @Bean
    public org.neo4j.ogm.config.Configuration neoConfig() throws URISyntaxException, IOException {
        String path = neoUri;
        org.neo4j.ogm.config.Configuration.Builder builder = new org.neo4j.ogm.config.Configuration.Builder();
        if (!"default".equals(path)) {
            path = path.replace('\\', '/');
            URI uri = new URI(path);
            if ("file".equals(uri.getScheme())) {
                File file = new File(uri.getSchemeSpecificPart());
                path = "file:///" + file.getCanonicalFile().getAbsolutePath().replace('\\', '/');
            }
            builder.uri(path);
        }
        return builder.build();
    }
}
