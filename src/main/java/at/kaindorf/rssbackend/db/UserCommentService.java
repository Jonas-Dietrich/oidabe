package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.RssChannel;
import at.kaindorf.rssbackend.pojos.RssItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Project: RssBackend
 * Created by: eibmac20
 * Date: 23.05.24
 * This class is a service for managing user comments.
 * It provides methods for posting and retrieving user comments.
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class UserCommentService {
    private final RssItemRepo rssItemRepo;
    private final UserCommentInitializer userCommentInitializer;

    /**
     * This method is used to post a new user comment.
     * It accepts a RssItem object representing the comment to be posted.
     *
     * @param rssItem The RssItem object representing the comment to be posted.
     * @return The posted RssItem object.
     * @throws RuntimeException if an error occurs while posting the comment.
     */
    public RssItem postComment(RssItem rssItem) throws RuntimeException {
        rssItem.setPubDate(LocalDateTime.now());
        RssChannel channel = userCommentInitializer.getUserCommentChannel();
        rssItem.setRssChannel(channel);
        return rssItemRepo.save(rssItem);
    }

    /**
     * This method is used to retrieve user comments.
     * It accepts a LocalDateTime object representing the date up to which comments should be retrieved.
     *
     * @param evaluateUpTo The date up to which comments should be retrieved.
     * @return A list of RssItem objects representing the user comments.
     * @throws RuntimeException if an error occurs while retrieving the comments.
     */
    public List<RssItem> getComments(LocalDateTime evaluateUpTo) throws RuntimeException {
        log.info(evaluateUpTo.toString());
        log.info(userCommentInitializer.getUserCommentChannel().getFeedUrl());
        return rssItemRepo.getItemsFromChannelAfterDate(evaluateUpTo, userCommentInitializer.getUserCommentChannel().getFeedUrl());
    }

    public Long countComments() {
        return rssItemRepo.countRssItemsByRssChannels(List.of(userCommentInitializer.getUserCommentChannel().getFeedUrl()));
    }


    public Page<RssItem> getCommentsPage(Pageable paging) throws RuntimeException {
        List<String> urls = List.of(userCommentInitializer.getUserCommentChannel().getFeedUrl());
        return rssItemRepo.getRssItemByUrls(urls, paging);
    }
}