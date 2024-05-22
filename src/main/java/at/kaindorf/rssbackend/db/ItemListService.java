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
    private final RssItemRepo rssItemRepo;
    private final RssUpdater rssUpdater;

    public List<RssItem> getFeedItems() throws RuntimeException {
        return rssItemRepo.findAll();
    }

    public List<RssItem> getFeedItems(List<String> urls) throws RuntimeException {
        rssUpdater.updateAllFeeds(urls);
        return rssItemRepo.getRssItemByUrls(urls);
    }

    public Optional<RssItem> getFeedItem(Long itemId) {
        return rssItemRepo.findById(itemId);
    }
}
