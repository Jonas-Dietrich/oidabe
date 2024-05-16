package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.RssItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Project: RSSBackend
 * Created by: diejoc20
 * Date: 12.04.24
 */
public interface RssItemRepo extends JpaRepository<RssItem, Long> {
    @Query("select item from RssItem item where item.rssChannel.feedUrl in :feedUrls")
    List<RssItem> getRssItemByUrls(List<String> feedUrls);
}
