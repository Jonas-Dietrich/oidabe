package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.RssChannel;
import at.kaindorf.rssbackend.pojos.RssImage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Project: RssBackend
 * Created by: eibmac20
 * Date: 23.05.24
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class UserCommentInitializer {
    private final RssChannelRepo rssChannelRepo;

    @Value("${API_COMMENT_URL}")
    private String API_COMMENT_URL;

    /**
     * This method is used to create a new comment channel if it does not already exist.
     * The comment channel is created with a predefined URL, title, and description.
     * The creation date is set to the current date and time.
     * The channel is then saved to the RssChannel repository.
     * If the channel already exists, no action is taken.
     */
    @PostConstruct
    private void createCommentChannel() {
        if (!rssChannelRepo.existsByFeedUrl(API_COMMENT_URL)) {
            String API_COMMENT_TITLE = "Really-Sophisticated-Story-Feed comments";
            String API_COMMENT_DESCRIPTION = "Comments for the Really-Sophisticated-Story-Feed. Feel free to leave your thoughts!";
            RssChannel commentChannel = new RssChannel(
                    API_COMMENT_URL,
                    API_COMMENT_TITLE,
                    API_COMMENT_DESCRIPTION,
                    LocalDateTime.now(),
                    new ArrayList<>(),
                    new RssImage(API_COMMENT_URL + "/images/rssChannelLogo.png", "Content credentials: Generated with AI ∙ 24 May 2024 at 2:32 pm")
            );
            rssChannelRepo.save(commentChannel);
            log.info("user comments channel created");
        }
    }

    /**
     * This method is used to retrieve the user comment channel.
     * The comment channel is identified by a predefined URL.
     *
     * @return The RssChannel object representing the user comment channel.
     */
    public RssChannel getUserCommentChannel() {
        return rssChannelRepo.findRssChannelByFeedUrl(API_COMMENT_URL);
    }
}
