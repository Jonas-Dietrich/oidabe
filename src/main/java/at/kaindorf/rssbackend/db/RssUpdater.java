package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.RssChannel;
import at.kaindorf.rssbackend.pojos.RssItem;
import at.kaindorf.rssbackend.pojos.RssOuter;
import jakarta.xml.bind.JAXB;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Project: RSSBackend
 * Created by: eibmac20
 * Date: 09.04.24
 * This class is responsible for initializing the database with RSS channel data.
 * It provides a method to load data from a given feed URL and persist it to the database.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RssUpdater {
    private final RssChannelRepo rssChannelRepo;
    private final RssItemRepo rssItemRepo;

    /**
     * Loads data from the specified feed URL and persists it to the database.
     * This method sets the channel for each RSS item in the channel,
     * establishing a relationship between the channel and its items.
     *
     * @param feedUrl the URL of the RSS feed to load data from
     * @throws RuntimeException if an error occurs during data loading or persistence
     */
    public void loadData(String feedUrl) throws RuntimeException {
        RssOuter rss = JAXB.unmarshal(feedUrl, RssOuter.class);
        RssChannel channel = rss.getChannel();
        rss.getChannel().getRssItems().forEach(s -> s.setRssChannel(channel));
        channel.setFeedUrl(feedUrl);
        if (!rssChannelRepo.existsByFeedUrl(feedUrl) || channel.getLastBuildDate() == null || !rssChannelRepo.findRssChannelByFeedUrl(feedUrl).getLastBuildDate().equals(channel.getLastBuildDate())) {
            List<RssItem> existingItems = rssItemRepo.findAll();
            channel.getRssItems().removeAll(existingItems);
            rssChannelRepo.save(channel);
        }
    }

    /**
     * Updates all the feeds from the provided list of URLs.
     * This method iterates over the list and calls the updateFeed method for each URL.
     *
     * @param urls the list of URLs of the RSS feeds to update
     * @throws RuntimeException if an error occurs during data loading or persistence
     */
    public void updateAllFeeds(List<String> urls) throws RuntimeException {
        for (String url : urls) {
            updateFeed(url);
        }
    }

    /**
     * Updates the feed from the provided URL.
     *
     * @param url the URL of the RSS feed to update
     * @throws RuntimeException if an error occurs during data loading or persistence
     */
    public void updateFeed(String url) throws RuntimeException {
        loadData(url);
    }
}
