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
    private final RssCategoryRepo rssCategoryRepo;

    /**
     * The frequency at which the API updates, in seconds.
     * This value is injected from the application properties.
     */
    @Value("${API_UPDATE_FREQUENCY}")
    private Integer API_UPDATE_FREQUENCY;

    /**
     * This method is used to retrieve an RssChannel object from a given feed URL.
     * It unmarshals the XML data from the feed URL into an RssOuter object, and retrieves the RssChannel from it.
     * The method then sets the feed URL and the last update time for the channel.
     * It also removes any existing items from the channel's item list, and collects the remaining items into a set.
     * The method then collects the enclosure URLs, categories, and sources from the items into separate sets.
     * For each item in the item set, it sets the category, enclosure URL, and source to the corresponding object from the respective sets.
     * If the item's publication date is null, it sets the publication date to the current date and time.
     * Finally, it sets the channel's category to the corresponding object from the category set.
     *
     * @param feedUrl The URL of the feed from which to retrieve the RssChannel.
     * @return The RssChannel object retrieved from the feed URL.
     */
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
        Set<RssEnclosureURL> rssEnclosureURLSet = rssItemSet.stream().map(RssItem::getEnclosureURL).filter(Objects::nonNull).collect(Collectors.toSet());

        // category
        Set<RssCategory> categorySet = new HashSet<>(rssCategoryRepo.findAll());
        if (channel.getCategory() != null) categorySet.add(channel.getCategory());
        categorySet.addAll(channel.getRssItems().stream().map(RssItem::getCategory).filter(Objects::nonNull).collect(Collectors.toSet()));

        // source
        Set<RssSource> rssSourceSet = rssItemSet.stream().map(RssItem::getSource).filter(Objects::nonNull).collect(Collectors.toSet());

        for (RssItem item : rssItemSet) {
            item.setCategory(categorySet.stream().filter(c -> c.equals(item.getCategory())).findAny().orElse(null));
            item.setEnclosureURL(rssEnclosureURLSet.stream().filter(e -> e.equals(item.getEnclosureURL())).findAny().orElse(null));
            item.setSource(rssSourceSet.stream().filter(s -> s.equals(item.getSource())).findAny().orElse(null));
            if (item.getPubDate() == null) item.setPubDate(LocalDateTime.now());
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
            log.info("adding " + feedUrl);
            rssChannelRepo.save(channel);
        }
        else if (rssChannelRepo.findRssChannelByFeedUrl(feedUrl).getLastUpdate().plusSeconds(API_UPDATE_FREQUENCY).isBefore(LocalDateTime.now()) ){
            RssChannel channel = getChannel(feedUrl);
            log.info("updating " + feedUrl);
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
            try {
                updateFeed(url);
            }
            catch (Exception e) {
                log.error("Error loading: " + url);
            }
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
