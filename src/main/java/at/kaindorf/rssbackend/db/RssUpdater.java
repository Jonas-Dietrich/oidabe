package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.RssChannel;
import at.kaindorf.rssbackend.pojos.RssOuter;
import jakarta.xml.bind.JAXB;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

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
    private final RssCategoryRepo rssCategoryRepo;

    @Value("${API_UPDATE_FREQUENCY}")
    private Integer API_UPDATE_FREQUENCY;

    public RssChannel getChannel(String feedUrl) {
        RssOuter rss = JAXB.unmarshal(feedUrl, RssOuter.class);
        RssChannel channel = rss.getChannel();
        rss.getChannel().getRssItems().forEach(s -> s.setRssChannel(channel));
        channel.setFeedUrl(feedUrl);
        channel.setLastUpdate(LocalDateTime.now());
        return channel;
    }

    /**
     * Loads data from the specified feed URL and persists it to the database.
     * This method sets the channel for each RSS item in the channel,
     * establishing a relationship between the channel and its items.
     *
     * @param feedUrl the URL of the RSS feed to load data from
     * @throws RuntimeException if an error occurs during data loading or persistence
     */
    public void loadData(String feedUrl) throws RuntimeException {
        if (!rssChannelRepo.existsByFeedUrl(feedUrl)) {
            RssChannel channel = getChannel(feedUrl);
            rssChannelRepo.save(channel);
        }
        else if (rssChannelRepo.findRssChannelByFeedUrl(feedUrl).getLastUpdate().plusSeconds(API_UPDATE_FREQUENCY).isBefore(LocalDateTime.now()) ){
            RssChannel channel = getChannel(feedUrl);
            if (channel.getLastBuildDate() == null || !rssChannelRepo.findRssChannelByFeedUrl(feedUrl).getLastBuildDate().equals(channel.getLastBuildDate())) rssChannelRepo.save(channel);
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
