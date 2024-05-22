package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.RssItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Project: RssBackend
 * Created by: eibmac20
 * Date: 16.05.24
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class ItemListService {
    // Repository for RssItem entities
    private final RssItemRepo rssItemRepo;
    // Service for updating RSS feeds
    private final RssUpdater rssUpdater;

    /**
     * Retrieves all RssItem entities from the repository.
     * @return List of RssItem entities
     * @throws RuntimeException if any error occurs during the retrieval
     */
    public List<RssItem> getFeedItems() throws RuntimeException {
        return rssItemRepo.findAll();
    }

    /**
     * Updates all RSS feeds with the provided URLs and retrieves the corresponding RssItem entities.
     * @param urls List of URLs to update and retrieve the RssItems for
     * @return List of RssItem entities corresponding to the provided URLs
     * @throws RuntimeException if any error occurs during the update or retrieval
     */
    public List<RssItem> getFeedItems(List<String> urls) throws RuntimeException {
        rssUpdater.updateAllFeeds(urls);
        return rssItemRepo.getRssItemByUrls(urls);
    }

    /**
     * Retrieves an RssItem entity with the provided ID from the repository.
     * @param itemId ID of the RssItem to retrieve
     * @return Optional containing the RssItem if found, empty Optional otherwise
     */
    public Optional<RssItem> getFeedItem(Long itemId) {
        return rssItemRepo.findById(itemId);
    }
}