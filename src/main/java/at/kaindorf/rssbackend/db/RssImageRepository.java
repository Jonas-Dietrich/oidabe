package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.RssImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RssImageRepository extends JpaRepository<RssImage, Long> {
}
