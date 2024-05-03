package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.RssImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RssImageRepo extends JpaRepository<RssImage, Long> {
}
