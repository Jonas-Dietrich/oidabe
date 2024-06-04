package at.kaindorf.rssbackend.web;

import at.kaindorf.rssbackend.db.ItemListService;
import at.kaindorf.rssbackend.pojos.ApiItemList;
import at.kaindorf.rssbackend.pojos.RssItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        if (urls == null) {
            rssItemList = itemListService.getFeedItems().stream().map(ApiItemList::new).collect(Collectors.toList());
        } else {
            rssItemList = itemListService.getFeedItems(urls).stream().map(ApiItemList::new).collect(Collectors.toList());
        }
        return ResponseEntity.ok(rssItemList);
    }

    @GetMapping("/pages")
    public ResponseEntity<Iterable<RssItem>> getRssItemsPage(
            @RequestParam(required = false) List<String> urls,
            @RequestParam(required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "item_id") String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        if (urls == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.ok(itemListService.getFeedItems(urls, paging));
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
        Optional<RssItem> optionalRssItem = itemListService.getFeedItem(itemId);
        return optionalRssItem.map(rssItem -> ResponseEntity.ok(new ApiItemList(rssItem))).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
