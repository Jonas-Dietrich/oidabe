package at.kaindorf.rssbackend.web;

import at.kaindorf.rssbackend.db.ItemListService;
import at.kaindorf.rssbackend.pojos.ApiItemList;
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
 * This class represents the REST controller for managing the item list.
 * It handles HTTP requests related to the item list resource.
*/

@RestController
@Slf4j
@RequestMapping("/item-list")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ItemListResource {
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
                rssItemList = itemListService.getFeed().stream().map(ApiItemList::new).collect(Collectors.toList());
            }
            else {
                rssItemList = itemListService.getFeed(urls).stream().map(ApiItemList::new).collect(Collectors.toList());
            }
            return ResponseEntity.ok(rssItemList);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
