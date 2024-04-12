package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.RssItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Project: RSSBackend
 * Created by: diejoc20
 * Date: 12.04.24
 */
public interface RssItemRepo extends JpaRepository<RssItem, Long> {
}
