package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.*;
import jakarta.xml.bind.JAXB;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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

    @Value("${API_UPDATE_FREQUENCY}")
    private Integer API_UPDATE_FREQUENCY;

    public RssChannel getChannel(String feedUrl) {
        RssOuter rss = JAXB.unmarshal(feedUrl, RssOuter.class);
        RssChannel channel = rss.getChannel();
        rss.getChannel().getRssItems().forEach(s -> s.setRssChannel(channel));
        channel.setFeedUrl(feedUrl);
        channel.setLastUpdate(LocalDateTime.now());

        List<RssItem> existingItems = rssItemRepo.findAll();
        channel.getRssItems().removeAll(existingItems);

        Set<RssItem> rssItemSet = channel.getRssItems().stream().filter(Objects::nonNull).collect(Collectors.toSet());

        // enclosure url
        Set<RssEnclosureURL> rssEnclosureURLSet = rssItemSet.stream().map(i -> i.getEnclosureURL()).filter(Objects::nonNull).collect(Collectors.toSet());

        // category
        Set<RssCategory> categorySet = new HashSet<>();
        if (channel.getCategory() != null) categorySet.add(channel.getCategory());
        categorySet.addAll(channel.getRssItems().stream().map(RssItem::getCategory).filter(Objects::nonNull).collect(Collectors.toSet()));

        // source
        Set<RssSource> rssSourceSet = rssItemSet.stream().map(i -> i.getSource()).filter(Objects::nonNull).collect(Collectors.toSet());

        for (RssItem item : rssItemSet) {
            item.setCategory(categorySet.stream().filter(c -> c.equals(item.getCategory())).findAny().orElse(null));
            item.setEnclosureURL(rssEnclosureURLSet.stream().filter(e -> e.equals(item.getEnclosureURL())).findAny().orElse(null));
            item.setSource(rssSourceSet.stream().filter(s -> s.equals(item.getSource())).findAny().orElse(null));
        }

        channel.setCategory(categorySet.stream().filter(c -> c.equals(channel.getCategory())).findAny().orElse(null));

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
