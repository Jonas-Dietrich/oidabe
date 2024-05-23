package at.kaindorf.rssbackend.pojos;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Project: RssBackend
 * Created by: eibmac20
 * Date: 21.05.24
 */
@Data
public class ApiChannelListItem {
    private String feedUrl;
    private String title;
    private String link;
    private String description;
    private LocalDateTime lastBuildDate;
    private RssImage rssImage;
    private RssCategory category;

    /**
     * Constructor for the ApiChannelListItem class.
     * It initializes the object with the data from the provided RssChannel object.
     *
     * @param channel The RssChannel object from which to initialize the ApiChannelListItem object.
     */
    public ApiChannelListItem(RssChannel channel) {
        this.feedUrl = channel.getFeedUrl();
        this.title = channel.getTitle();
        this.link = channel.getLink();
        this.description = channel.getDescription();
        this.lastBuildDate = channel.getLastBuildDate();
        this.rssImage = channel.getRssImage();
        this.category = channel.getCategory();
    }
}
