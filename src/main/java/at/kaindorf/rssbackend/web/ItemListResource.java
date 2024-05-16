package at.kaindorf.rssbackend.web;

import at.kaindorf.rssbackend.db.ItemListService;
import at.kaindorf.rssbackend.pojos.RssItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Project: RSSBackend
 * Created by: eibmac20
 * Date: 16.05.24
*/

@RestController
@Slf4j
@RequestMapping("/item-list")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ItemListResource {
    private final ItemListService itemListService;

    @GetMapping
    public ResponseEntity<Iterable<RssItem>> getRssItems(@RequestParam(required = false) List<String> urls) {
        List<RssItem> rssItemList;

        try {
            if (urls == null || urls.isEmpty()) {
                rssItemList = itemListService.getFeed();
            }
            else {
                rssItemList = itemListService.getFeed(urls);
            }
            return ResponseEntity.ok(rssItemList);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
