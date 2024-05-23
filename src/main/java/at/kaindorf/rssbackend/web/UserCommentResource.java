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
 */

@RestController
@Slf4j
@RequestMapping("/comments")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserCommentResource {
    private final UserCommentService userCommentService;

    @GetMapping("/{evaluateUpTo}")
    public ResponseEntity<Iterable<ApiItemList>> getUserComments(@PathVariable(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate evaluateUpTo) {
        return ResponseEntity.ok(userCommentService.getComments(evaluateUpTo.atStartOfDay()).stream().map(ApiItemList::new).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<RssItem> postUserComment(@RequestBody RssItem comment) {
        return ResponseEntity.ok(userCommentService.postComment(comment));
    }
}
