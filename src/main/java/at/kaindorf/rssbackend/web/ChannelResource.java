package at.kaindorf.rssbackend.web;

import at.kaindorf.rssbackend.db.FeedListService;
import at.kaindorf.rssbackend.db.RssItemRepo;
import at.kaindorf.rssbackend.pojos.ApiChannelListItem;
import at.kaindorf.rssbackend.pojos.ChannelCount;
import at.kaindorf.rssbackend.pojos.RssChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
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
public class ChannelResource {
    private final FeedListService feedListService;
    private final RssItemRepo rssItemRepo;

    /**
     * Retrieves all RSS channels from the feed list.
     * If the 'urls' parameter is provided, it filters the channels based on the given URLs.
     *
     * @param urls The list of URLs to filter the channels (optional)
     * @return A ResponseEntity containing the list of RSS channels
     */
    @GetMapping
    public ResponseEntity<Iterable<ApiChannelListItem>> getAllChannels(@RequestParam(required = false) List<String> urls) {
        List<ApiChannelListItem> rssChannelList;

        if (urls == null) {
            rssChannelList = feedListService.getChannels().stream().map(ApiChannelListItem::new).collect(Collectors.toList());
        } else {
            rssChannelList = feedListService.getChannels(urls).stream().map(ApiChannelListItem::new).collect(Collectors.toList());
        }
        return ResponseEntity.ok(rssChannelList);
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
    public ResponseEntity<ApiChannelListItem> addChannel(@RequestParam String url) {
        RssChannel rssChannel = feedListService.getChannel(url);
        if (rssChannel == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(new ApiChannelListItem(rssChannel));
    }

    @GetMapping("/topChannels")
    public ResponseEntity<Iterable<ChannelCount>> getTopTen(@RequestParam(required = false, defaultValue = "10") Integer numOfChannels) {
        return ResponseEntity.ok(rssItemRepo.getTopChannels(PageRequest.of(0, numOfChannels)));
    }
}
