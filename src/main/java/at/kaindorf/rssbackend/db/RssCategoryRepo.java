package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.RssCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RssCategoryRepo extends JpaRepository<RssCategory, Long> {
}
