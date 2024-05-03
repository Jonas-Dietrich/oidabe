package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.RssChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RssChannelRepo extends JpaRepository<RssChannel, Long> {

    @Query("SELECT COUNT(e) > 0 FROM RssChannel e WHERE e.feedUrl = :feedUrl")
    boolean existsByFeedUrl(String feedUrl);
}
