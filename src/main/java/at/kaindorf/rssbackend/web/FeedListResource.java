package at.kaindorf.rssbackend.web;

import at.kaindorf.rssbackend.db.FeedListService;
import at.kaindorf.rssbackend.pojos.ApiChannelList;
import at.kaindorf.rssbackend.pojos.RssChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Project: RSSBackend
 * Created by: eibmac20
 * Date: 16.05.24
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
    public ResponseEntity<Iterable<ApiChannelList>> getAllChannels(@RequestParam(required = false) List<String> urls) {
        List<ApiChannelList> rssChannelList;

        try {
            if (urls == null || urls.isEmpty()) {
                rssChannelList = feedListService.getChannels().stream().map(ApiChannelList::new).collect(Collectors.toList());
            } else {
                rssChannelList = feedListService.getChannels(urls).stream().map(ApiChannelList::new).collect(Collectors.toList());
            }
            return ResponseEntity.ok(rssChannelList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Adds a new RSS channel to the feed list.
     * The channel is identified by the provided URL.
     * If the channel does not exist, a 404 Not Found status is returned.
     * In case of any other exception, a 500 Internal Server Error status is returned.
     *
     * @param url The URL of the RSS channel to add
     * @return A ResponseEntity containing the added RSS channel wrapped in an ApiChannelList object
     * or an error status
     */
    @PostMapping
    public ResponseEntity<ApiChannelList> addChannel(@RequestParam String url) {
        try {
            RssChannel rssChannel = feedListService.getChannel(url);
            if (rssChannel == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok().body(new ApiChannelList(rssChannel));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
