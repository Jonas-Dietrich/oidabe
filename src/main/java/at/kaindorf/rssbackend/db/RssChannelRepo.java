package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.RssChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RssChannelRepo extends JpaRepository<RssChannel, Long> {

    /**
     * Finds an RssChannel by its feed URL.
     *
     * @param feedUrl The feed URL of the RssChannel to find.
     * @return The RssChannel with the given feed URL, or null if no such RssChannel exists.
     */
    @Query("select channel from RssChannel channel where channel.feedUrl = :feedUrl")
    RssChannel findRssChannelByFeedUrl(String feedUrl);

    /**
     * Checks if an RssChannel with the given feed URL exists.
     *
     * @param feedUrl The feed URL to check for existence.
     * @return true if an RssChannel with the given feed URL exists, false otherwise.
     */
    @Query("SELECT COUNT(e) > 0 FROM RssChannel e WHERE e.feedUrl = :feedUrl")
    boolean existsByFeedUrl(String feedUrl);
}