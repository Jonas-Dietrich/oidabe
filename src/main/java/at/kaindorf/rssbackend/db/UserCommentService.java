package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.RssChannel;
import at.kaindorf.rssbackend.pojos.RssItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Project: RssBackend
 * Created by: eibmac20
 * Date: 23.05.24
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class UserCommentService {
    private final RssItemRepo rssItemRepo;
    private final UserCommentInitializer userCommentInitializer;

    public RssItem postComment(RssItem rssItem) throws RuntimeException {
        rssItem.setItem_id(null);
        rssItem.setPubDate(LocalDateTime.now());
        RssChannel channel = userCommentInitializer.getUserCommentChannel();
        rssItem.setRssChannel(channel);
        return rssItemRepo.save(rssItem);
    }

    public List<RssItem> getComments(LocalDateTime evaluateUpTo) throws RuntimeException {
        log.info(evaluateUpTo.toString());
        log.info(userCommentInitializer.getUserCommentChannel().getFeedUrl());
        return rssItemRepo.getItemsFromChannelAfterDate(evaluateUpTo, userCommentInitializer.getUserCommentChannel().getFeedUrl());
    }
}
