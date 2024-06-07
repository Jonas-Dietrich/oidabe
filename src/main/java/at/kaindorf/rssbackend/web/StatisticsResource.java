package at.kaindorf.rssbackend.web;

import at.kaindorf.rssbackend.db.*;
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

    @GetMapping("/count/channel")
    public ResponseEntity<Long> countChannels() {
        return ResponseEntity.ok(rssChannelRepo.count());
    }

    @GetMapping("/count/items")
    public ResponseEntity<Long> countItems() {
        return ResponseEntity.ok(rssItemRepo.count());
    }

    @GetMapping("/count/comments")
    public ResponseEntity<Long> countComments() {
        return ResponseEntity.ok(userCommentService.countComments());
    }

    @GetMapping("/count/images")
    public ResponseEntity<Long> countImages() {
        return ResponseEntity.ok(rssImageRepo.count());
    }

    @GetMapping("/count/categories")
    public ResponseEntity<Long> countCategories() {
        return ResponseEntity.ok(rssCategoryRepo.count());
    }

    @GetMapping("/count/sources")
    public ResponseEntity<Long> countSources() {
        return ResponseEntity.ok(rssSourceRepo.count());
    }

    @GetMapping("/count/enclosure-urls")
    public ResponseEntity<Long> countEnclosureUrls() {
        return ResponseEntity.ok(rssEnclosureURLRepo.count());
    }
}
