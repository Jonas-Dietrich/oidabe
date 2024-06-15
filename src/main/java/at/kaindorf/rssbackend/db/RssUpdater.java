package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.*;
import jakarta.xml.bind.JAXB;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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

    private final Map<String, Object> locks = new ConcurrentHashMap<>();

    /**
     * The size of the thread pool used for updating RSS feeds.
     * This value is injected from the application properties.
     * The thread pool size determines the maximum number of concurrent tasks that can be executed when updating feeds.
     * If more tasks are submitted than the size of the thread pool, they will be queued and executed as threads become available.
     */
    @Value("${THREAD_POOL_SIZE:10}")
    private Integer FEED_THREAD_POOL_SIZE;

    /**
     * The frequency at which the API updates, in seconds.
     * This value is injected from the application properties.
     */
    @Value("${API_UPDATE_FREQUENCY:300}")
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

        List<RssItem> existingItems = rssItemRepo.getLatestRssItems(feedUrl);
        channel.getRssItems().removeAll(existingItems);

        Set<RssItem> rssItemSet = channel.getRssItems().stream().filter(Objects::nonNull).collect(Collectors.toSet());

        // enclosure url
        Set<RssEnclosureURL> rssEnclosureURLSet = rssItemSet.stream().map(RssItem::getEnclosureURL).filter(Objects::nonNull).collect(Collectors.toSet());

        // category
        Set<RssCategory> categorySet = new HashSet<>(rssCategoryRepo.findAll());
        if (channel.getCategory() != null) categorySet.add(channel.getCategory());
        categorySet.addAll(rssItemSet.stream().map(RssItem::getCategory).filter(Objects::nonNull).collect(Collectors.toSet()));

        // source
        Set<RssSource> rssSourceSet = rssItemSet.stream().map(RssItem::getSource).filter(Objects::nonNull).collect(Collectors.toSet());

        for (RssItem item : rssItemSet) {
            item.setCategory(categorySet.stream().filter(c -> c.equals(item.getCategory())).findAny().orElse(null));
            item.setEnclosureURL(rssEnclosureURLSet.stream().filter(e -> e.equals(item.getEnclosureURL())).findAny().orElse(null));
            item.setSource(rssSourceSet.stream().filter(s -> s.equals(item.getSource())).findAny().orElse(null));
            if (item.getPubDate() == null) item.setPubDate(LocalDateTime.now());
        }

        channel.setRssItems(new ArrayList<>(rssItemSet));

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
        Object lock = locks.computeIfAbsent(feedUrl, k -> new Object());

        try {
            synchronized (lock) {
                if (!rssChannelRepo.existsByFeedUrl(feedUrl)) {
                    RssChannel channel = getChannel(feedUrl);
                    log.info(feedUrl + " adding");
                    rssChannelRepo.save(channel);
                    log.info(feedUrl + " initialized with " + channel.getRssItems().size() + " items");
                } else if (rssChannelRepo.findRssChannelByFeedUrl(feedUrl).getLastUpdate().plusSeconds(API_UPDATE_FREQUENCY).isBefore(LocalDateTime.now())) {
                    RssChannel channel = getChannel(feedUrl);
                    log.info(feedUrl + " updating");
                    if (channel.getLastBuildDate() == null || !rssChannelRepo.findRssChannelByFeedUrl(feedUrl).getLastBuildDate().equals(channel.getLastBuildDate()))
                        rssChannelRepo.save(channel);
                    log.info(feedUrl + " added " + channel.getRssItems().size() + " new items");
                }
                else {
                    log.info("skipping " + feedUrl);
                }
            }
        } finally {
            locks.remove(feedUrl);
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
        urls = new ArrayList<>(new HashSet<>(urls));

        ExecutorService executorService = Executors.newFixedThreadPool(FEED_THREAD_POOL_SIZE);
        try {
            for (String url : urls) {
                executorService.submit(() -> {
                    try {
                        log.info("calling " + url);
                        updateFeed(url);
                    } catch (Exception e) {
                        log.error("Error loading: " + url);
                    }
                });
            }
        } finally {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
                    throw new RuntimeException("Not all tasks completed");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted", e);
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
