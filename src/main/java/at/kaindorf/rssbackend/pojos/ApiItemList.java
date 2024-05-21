package at.kaindorf.rssbackend.pojos;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Project: RssBackend
 * Created by: eibmac20
 * Date: 21.05.24
 */
@Data
public class ApiItemList {
    private Long item_id;

    private String title;
    private String link;
    private String description;
    private ApiChannelList rssChannel;
    private LocalDateTime pubDate;
    private String author;

    public ApiItemList(RssItem rssItem) {
        item_id = rssItem.getItem_id();
        link = rssItem.getLink();
        description = rssItem.getDescription();
        rssChannel = new ApiChannelList(rssItem.getRssChannel());
        pubDate = rssItem.getPubDate();
        author = rssItem.getAuthor();
    }
}
