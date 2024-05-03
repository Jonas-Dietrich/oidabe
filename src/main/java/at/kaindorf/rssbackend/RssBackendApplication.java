package at.kaindorf.rssbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("at.kaindorf.rssbackend.pojos")
public class RssBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(RssBackendApplication.class, args);
    }

}
