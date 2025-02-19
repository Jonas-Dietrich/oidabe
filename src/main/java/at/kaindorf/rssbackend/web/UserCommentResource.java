package at.kaindorf.rssbackend.web;

import at.kaindorf.rssbackend.db.UserCommentService;
import at.kaindorf.rssbackend.pojos.ApiItemList;
import at.kaindorf.rssbackend.pojos.RssItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * Project: RssBackend
 * Created by: eibmac20
 * Date: 23.05.24
 * This class is a REST controller for managing user comments.
 * It provides endpoints for getting and posting user comments.
 */

@RestController
@Slf4j
@RequestMapping("/comments")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserCommentResource {
    private final UserCommentService userCommentService;

    /**
     * This method is a GET endpoint for retrieving user comments.
     * It accepts an optional date parameter for filtering comments up to a certain date.
     *
     * @param evaluateUpTo The date up to which comments should be retrieved. If not provided, all comments are retrieved.
     * @return A ResponseEntity containing a list of ApiItemList objects representing the user comments.
     */
    @GetMapping("/{evaluateUpTo}")
    public ResponseEntity<Iterable<ApiItemList>> getUserComments(@PathVariable(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate evaluateUpTo) {
        return ResponseEntity.ok(userCommentService.getComments(evaluateUpTo.atStartOfDay()).stream().map(ApiItemList::new).collect(Collectors.toList()));
    }

    /**
     * This method is a POST endpoint for posting a new user comment.
     * It accepts a RssItem object representing the comment to be posted.
     *
     * @param comment The RssItem object representing the comment to be posted.
     * @return A ResponseEntity containing the posted RssItem object.
     */
    @PostMapping
    public ResponseEntity<RssItem> postUserComment(@RequestBody RssItem comment) {
        return ResponseEntity.ok(userCommentService.postComment(comment));
    }

    /**
     * This method is a GET endpoint for retrieving a paginated list of RssItem comments.
     * It accepts optional parameters for page number, page size, sorting field, and sorting direction.
     *
     * @param pageNo The number of the page to retrieve. If not provided, defaults to 0.
     * @param pageSize The size of the page to retrieve. If not provided, defaults to 10.
     * @param sortBy The field by which the results should be sorted. If not provided, defaults to "itemId".
     * @param asc A boolean indicating whether the results should be sorted in ascending order. If not provided, defaults to true.
     * @return A ResponseEntity containing a list of RssItem objects representing the comments on the requested page.
     */
    @GetMapping("/pages")
    public ResponseEntity<Iterable<RssItem>> getRssCommentsPage(
            @RequestParam(required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "itemId") String sortBy,
            @RequestParam(required = false, defaultValue = "true") Boolean asc) {

        Sort.Direction direction = asc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));

        return ResponseEntity.ok(userCommentService.getCommentsPage(paging));
    }
}