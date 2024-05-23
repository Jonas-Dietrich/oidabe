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
    private ApiChannelListItem rssChannel;
    private LocalDateTime pubDate;
    private String author;

    /**
     * Constructor for the ApiItemList class.
     * It initializes the object with the data from the provided RssItem object.
     *
     * @param rssItem The RssItem object from which to initialize the ApiItemList object.
     */
    public ApiItemList(RssItem rssItem) {
        item_id = rssItem.getItem_id();
        title = rssItem.getTitle();
        link = rssItem.getLink();
        description = rssItem.getDescription();
        rssChannel = new ApiChannelListItem(rssItem.getRssChannel());
        pubDate = rssItem.getPubDate();
        author = rssItem.getAuthor();
    }
}
