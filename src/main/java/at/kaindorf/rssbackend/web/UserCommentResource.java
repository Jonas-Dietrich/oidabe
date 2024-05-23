package at.kaindorf.rssbackend.web;

import at.kaindorf.rssbackend.db.UserCommentService;
import at.kaindorf.rssbackend.pojos.ApiItemList;
import at.kaindorf.rssbackend.pojos.RssItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * Project: RssBackend
 * Created by: eibmac20
 * Date: 23.05.24
 *
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
}