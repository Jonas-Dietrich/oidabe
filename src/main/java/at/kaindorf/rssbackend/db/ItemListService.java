package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.RssItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<RssItem> getFeed() throws Exception {
        return rssItemRepo.findAll();
    }

    public List<RssItem> getFeed(List<String> urls) throws Exception {
        rssUpdater.updateAllFeeds(urls);
        return rssItemRepo.getRssItemByUrls(urls);
    }
}
