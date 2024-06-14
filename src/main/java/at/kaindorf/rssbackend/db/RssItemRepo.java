package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.ChannelCount;
import at.kaindorf.rssbackend.pojos.RssChannel;
import at.kaindorf.rssbackend.pojos.RssItem;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
     * Retrieves the latest RssItems from a specific channel.
     *
     * @param feedUrl The feed URL of the channel to retrieve RssItems from.
     * @return A list of the latest RssItems from the specified channel, ordered by publication date in descending order.
     */
    @Query("select item from RssItem item where item.rssChannel.feedUrl = :feedUrl order by item.pubDate desc")
    List<RssItem> getLatestRssItems(String feedUrl);

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

    /**
     * This method retrieves a list of tuples representing the top channels and their respective count of RssItems.
     * Each tuple contains the RssChannel and the count of RssItems for that channel.
     * The result is sorted in descending order based on the count of RssItems.
     *
     * @param pageable The pagination information (page number, size, sorting)
     * @return A list of tuples, each containing an RssChannel and its respective count of RssItems.
     */
    @Query("select item.rssChannel as rssChannel, count(item) as count from RssItem item group by item.rssChannel order by count(item) desc")
    List<Tuple> getTopChannelsTuple(Pageable pageable);

    /**
     * This method is used to get the top channels based on the number of RssItems they have.
     * It uses the getTopChannelsTuple method to retrieve the data and then maps the result into a list of ChannelCount objects.
     * The ChannelCount object contains the RssChannel and the count of RssItems for that channel.
     * The result is sorted in descending order based on the count of RssItems.
     *
     * @param pageable The pagination information (page number, size, sorting)
     * @return A list of ChannelCount objects representing the top channels and their respective RssItems count.
     */
    default List<ChannelCount> getTopChannels(Pageable pageable) {
        return getTopChannelsTuple(pageable).stream()
                .map(tuple -> new ChannelCount(
                        (RssChannel) tuple.get("rssChannel"),
                        (Long) tuple.get("count")
                ))
                .collect(Collectors.toList());
    }
}
