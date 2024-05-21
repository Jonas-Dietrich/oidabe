package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.RssChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Project: RssBackend
 * Created by: eibmac20
 * Date: 16.05.24
 * This class represents a service for managing RSS feed lists.
 * It provides methods for retrieving and filtering RSS channels.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class FeedListService {
    private final RssChannelRepo rssChannelRepo;
    private final RssUpdater rssUpdater;

    /**
     * Retrieves all RSS channels from the database.
     *
     * @return a list of all RSS channels
     * @throws Exception if an error occurs while retrieving the channels
     */
    public List<RssChannel> getChannels() throws Exception {
        return rssChannelRepo.findAll();
    }

    /**
     * Retrieves RSS channels based on the provided URLs.
     * Loads data from the URLs into the database if necessary.
     *
     * @param urls the list of URLs to retrieve channels from
     * @return a list of RSS channels matching the provided URLs
     * @throws Exception if an error occurs while retrieving or loading the channels
     */
    public List<RssChannel> getChannels(List<String> urls) throws Exception {
        rssUpdater.updateAllFeeds(urls);
        return getChannels().stream().filter(c -> urls.contains(c.getFeedUrl())).collect(Collectors.toList());
    }

    public RssChannel getChannel(String url) throws Exception {
        rssUpdater.updateFeed(url);
        return rssChannelRepo.findRssChannelByFeedUrl(url);
    }
}
