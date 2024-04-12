package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.RssChannel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RssChannelRepo extends JpaRepository<RssChannel, Long> {
}
