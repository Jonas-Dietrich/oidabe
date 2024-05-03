package at.kaindorf.rssbackend.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RssEnclosureURLRepo extends JpaRepository<at.kaindorf.rssbackend.pojos.RssEnclosureURL, Long> {
}
