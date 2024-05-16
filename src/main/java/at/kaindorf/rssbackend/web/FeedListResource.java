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
 * Project: RssBackend
 * Created by: eibmac20
 * Date: 16.05.24
 */

@RestController
@Slf4j
@RequestMapping("/feed-list")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class FeedListResource {
    private final FeedListService feedListService;

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
