package at.kaindorf.rssbackend.web;

import at.kaindorf.rssbackend.db.ItemListService;
import at.kaindorf.rssbackend.pojos.ApiItemList;
import at.kaindorf.rssbackend.pojos.RssItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Project: RSSBackend
 * Created by: eibmac20
 * Date: 16.05.24
 * This class represents the REST controller for managing the item list.
 * It handles HTTP requests related to the item list resource.
 */

@RestController
@Slf4j
@RequestMapping("/item-list")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ItemResource {
    private final ItemListService itemListService;

    /**
     * This method retrieves all RSS items from the item list.
     * If the 'urls' parameter is provided, it filters the items based on the given URLs.
     *
     * @param urls The list of URLs to filter the items (optional)
     * @return A ResponseEntity containing the list of RSS items
     */
    @GetMapping
    public ResponseEntity<Iterable<ApiItemList>> getRssItems(@RequestParam(required = false) List<String> urls) {
        List<ApiItemList> rssItemList;

        try {
            if (urls == null || urls.isEmpty()) {
                rssItemList = itemListService.getFeedItems().stream().map(ApiItemList::new).collect(Collectors.toList());
            } else {
                rssItemList = itemListService.getFeedItems(urls).stream().map(ApiItemList::new).collect(Collectors.toList());
            }
            return ResponseEntity.ok(rssItemList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * This method retrieves a specific RSS item from the item list based on the provided item ID.
     * If the item with the given ID does not exist, it returns a 404 Not Found HTTP status.
     * In case of any other exception, it returns a 500 Internal Server Error HTTP status.
     *
     * @param itemId The ID of the RSS item to retrieve
     * @return A ResponseEntity containing the RSS item if found, otherwise an HTTP status indicating the error
     */
    @GetMapping("/{itemId}")
    public ResponseEntity<ApiItemList> getRssItem(@PathVariable Long itemId) {
        try {
            Optional<RssItem> optionalRssItem = itemListService.getFeedItem(itemId);
            return optionalRssItem.map(rssItem -> ResponseEntity.ok(new ApiItemList(rssItem))).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
