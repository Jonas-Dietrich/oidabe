package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.RssItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Project: RSSBackend
 * Created by: diejoc20
 * Date: 12.04.24
 */
public interface RssItemRepo extends JpaRepository<RssItem, Long> {
    /**
     * Retrieves a list of RssItems based on the provided feed URLs.
     *
     * @param feedUrls The list of feed URLs to retrieve RssItems from.
     * @return A list of RssItems that belong to the channels with the provided feed URLs.
     */
    @Query("select item from RssItem item where item.rssChannel.feedUrl in :feedUrls order by item.pubDate desc")
    List<RssItem> getRssItemByUrls(List<String> feedUrls);

    /**
     * Retrieves all Items that have been published after the provided date from a specific channel.
     *
     * @param evaluateUpTo The date to evaluate the items against.
     * @param feedUrl The feed that should be evaluated.
     * @return A list of RssItems that have been published after the provided date.
     */
    @Query("select item from RssItem item where item.pubDate >= :evaluateUpTo and item.rssChannel.feedUrl = :feedUrl order by item.pubDate desc")
    List<RssItem> getItemsFromChannelAfterDate(LocalDateTime evaluateUpTo, String feedUrl);

    /**
     * Retrieves a paginated list of RssItems based on the provided feed URLs.
     *
     * @param feedUrls The list of feed URLs to retrieve RssItems from.
     * @param pageable The pagination information (page number, size, sorting)
     * @return A page of RssItems that belong to the channels with the provided feed URLs.
     */
    @Query("select item from RssItem item where item.rssChannel.feedUrl in :feedUrls")
    Page<RssItem> getRssItemByUrls(List<String> feedUrls, Pageable pageable);

    /**
     * Counts the number of RssItems that belong to the channels with the provided feed URLs.
     *
     * @param feedUrls The list of feed URLs to count RssItems from.
     * @return The number of RssItems that belong to the channels with the provided feed URLs.
     */
    @Query("select count(*) from RssItem item where item.rssChannel.feedUrl in :feedUrls")
    Long countRssItemsByRssChannels(List<String> feedUrls);
}
