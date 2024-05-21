package at.kaindorf.rssbackend.pojos;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Project: RssBackend
 * Created by: eibmac20
 * Date: 21.05.24
 */
@Data
public class ApiChannelList {
    private String feedUrl;
    private String title;
    private String link;
    private String description;
    private LocalDateTime lastBuildDate;
    private RssImage rssImage;
    private RssCategory category;

    public ApiChannelList(RssChannel channel) {
        this.feedUrl = channel.getFeedUrl();
        this.title = channel.getTitle();
        this.link = channel.getLink();
        this.description = channel.getDescription();
        this.lastBuildDate = channel.getLastBuildDate();
        this.rssImage = channel.getRssImage();
        this.category = channel.getCategory();
    }
}
