package de.adesso.squadmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class SquadmapApplication {

    private static Logger logger = Logger.getAnonymousLogger();

    public static void main(String[] args) {
        SpringApplication.run(SquadmapApplication.class, args);
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec("rundll32 url.dll,FileProtocolHandler " + "http://localhost:8080/map");
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Could not open browser", exception);
        }
    }
}
