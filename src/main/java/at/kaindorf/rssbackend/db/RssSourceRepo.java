package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.RssSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RssSourceRepo extends JpaRepository<RssSource, Long> {
}
