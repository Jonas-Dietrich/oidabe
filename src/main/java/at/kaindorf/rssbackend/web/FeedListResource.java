package at.kaindorf.rssbackend.web;

import at.kaindorf.rssbackend.db.FeedListService;
import at.kaindorf.rssbackend.pojos.RssChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents the REST controller for managing the feed list.
 * It handles HTTP requests related to the feed list resource.
 */
@RestController
@Slf4j
@RequestMapping("/feed-list")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class FeedListResource {
    private final FeedListService feedListService;

    /**
     * Retrieves all RSS channels from the feed list.
     * If the 'urls' parameter is provided, it filters the channels based on the given URLs.
     *
     * @param urls The list of URLs to filter the channels (optional)
     * @return A ResponseEntity containing the list of RSS channels
     */
    @GetMapping
    public ResponseEntity<Iterable<RssChannel>> getAllChannels(@RequestParam(required = false) List<String> urls) {
        List<RssChannel> rssChannelList;

        log.info("request to feed-list");

        try {
            if (urls == null || (urls != null && urls.isEmpty())) {
                rssChannelList = feedListService.getChannels();
            }
            else {
                rssChannelList = feedListService.getChannels(urls);
            }
            return ResponseEntity.ok(rssChannelList);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
