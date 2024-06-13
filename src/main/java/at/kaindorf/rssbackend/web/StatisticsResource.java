package at.kaindorf.rssbackend.web;

import at.kaindorf.rssbackend.db.*;
import at.kaindorf.rssbackend.pojos.ApiAboutStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Project: RssBackend
 * Created by: eibmac20
 * Date: 07.06.24
 */

@RestController
@Slf4j
@RequestMapping("/stats")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class StatisticsResource {
    private final RssChannelRepo rssChannelRepo;
    private final RssItemRepo rssItemRepo;
    private final UserCommentService userCommentService;
    private final RssImageRepo rssImageRepo;
    private final RssCategoryRepo rssCategoryRepo;
    private final RssSourceRepo rssSourceRepo;
    private final RssEnclosureURLRepo rssEnclosureURLRepo;

    /**
     * Endpoint to get the count of channels.
     * @return ResponseEntity containing the count of channels.
     */
    @GetMapping("/count/channel")
    public ResponseEntity<Long> countChannels() {
        return ResponseEntity.ok(rssChannelRepo.count());
    }

    /**
     * Endpoint to get the count of items.
     * @return ResponseEntity containing the count of items.
     */
    @GetMapping("/count/items")
    public ResponseEntity<Long> countItems() {
        return ResponseEntity.ok(rssItemRepo.count());
    }

    /**
     * Endpoint to get the count of comments.
     * @return ResponseEntity containing the count of comments.
     */
    @GetMapping("/count/comments")
    public ResponseEntity<Long> countComments() {
        return ResponseEntity.ok(userCommentService.countComments());
    }

    /**
     * Endpoint to get the count of images.
     * @return ResponseEntity containing the count of images.
     */
    @GetMapping("/count/images")
    public ResponseEntity<Long> countImages() {
        return ResponseEntity.ok(rssImageRepo.count());
    }

    /**
     * Endpoint to get the count of categories.
     * @return ResponseEntity containing the count of categories.
     */
    @GetMapping("/count/categories")
    public ResponseEntity<Long> countCategories() {
        return ResponseEntity.ok(rssCategoryRepo.count());
    }

    /**
     * Endpoint to get the count of sources.
     * @return ResponseEntity containing the count of sources.
     */
    @GetMapping("/count/sources")
    public ResponseEntity<Long> countSources() {
        return ResponseEntity.ok(rssSourceRepo.count());
    }

    /**
     * Endpoint to get the count of enclosure URLs.
     * @return ResponseEntity containing the count of enclosure URLs.
     */
    @GetMapping("/count/enclosure-urls")
    public ResponseEntity<Long> countEnclosureUrls() {
        return ResponseEntity.ok(rssEnclosureURLRepo.count());
    }

    /**
     * Endpoint to get the statistics for the about page.
     * @return ResponseEntity containing the statistics for the about page.
     */
    @GetMapping("/count/aboutPage")
    public ResponseEntity<ApiAboutStats> getApiAboutStats() {
        Long channelCount = rssChannelRepo.count();
        Long itemCount = rssItemRepo.count();
        Long commentCount = userCommentService.countComments();
        Long categoryCount = rssCategoryRepo.count();
        Long sourceCount = rssSourceRepo.count();
        Long enclosureUrlCount = rssEnclosureURLRepo.count();

        ApiAboutStats stats = new ApiAboutStats(channelCount, commentCount, itemCount, categoryCount, sourceCount, enclosureUrlCount);

        return ResponseEntity.ok(stats);
    }
}
