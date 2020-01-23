package de.adesso.squadmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@EnableNeo4jRepositories()
public class SquadmapApplication {

    private static Logger logger = Logger.getAnonymousLogger();

    public static void main(String[] args) {
        SpringApplication.run(SquadmapApplication.class, args);
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec("rundll32 url.dll,FileProtocolHandler " + "http://localhost:4200/");
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "could not open browser", exception);
        }
    }
}
