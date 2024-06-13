package at.kaindorf.rssbackend.pojos;

import at.kaindorf.rssbackend.util.LocalDateConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Project: RSSBackend
 * Created by: diejoc20
 * Date: 09.04.24
 * The RssChannel class represents a single RSS feed channel.
 * It includes details about the channel such as the feed URL, title, link, description, last build date, image, items, category, and other metadata.
 * This class is also a JPA entity, meaning instances of this class can be automatically persisted in a database.
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RssChannel {
    /**
     * Constructor for the RssChannel class.
     * It initializes the object with the data provided.
     *
     * @param feedUrl The feed URL of the RSS channel.
     * @param title The title of the RSS channel.
     * @param description The description of the RSS channel.
     * @param lastBuildDate The last build date of the RSS channel.
     * @param rssItems The list of items in the RSS channel.
     * @param rssImage The image of the Rss channel
     */
    public RssChannel(String feedUrl, String title, String description, LocalDateTime lastBuildDate, List<RssItem> rssItems, RssImage rssImage) {
        this.feedUrl = feedUrl;
        this.title = title;
        this.description = description;
        this.lastBuildDate = lastBuildDate;
        this.rssItems = rssItems;
        this.rssImage = rssImage;
    }

    private LocalDateTime lastUpdate = LocalDateTime.now();

    @EqualsAndHashCode.Include
    @Id
    private String feedUrl;

    @Column(columnDefinition = "TEXT")
    private String title;
    @Column(columnDefinition = "TEXT")
    private String link;
    @Column(columnDefinition = "TEXT")
    private String description;

    @XmlJavaTypeAdapter(LocalDateConverter.class)
    private LocalDateTime lastBuildDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @XmlElement(name = "image")
    private RssImage rssImage;

    @OneToMany(mappedBy = "rssChannel", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @XmlElement(name = "item")
    @JsonIgnore
    private List<RssItem> rssItems;

    @ManyToOne
    private RssCategory category;

    @Column(columnDefinition = "TEXT")
    private String copyright;
    @Column(columnDefinition = "TEXT")
    private String docs;
    @Column(columnDefinition = "TEXT")
    private String generator;
    @Column(columnDefinition = "TEXT")
    private String language;
    @Column(columnDefinition = "TEXT")
    private String managingEditor;
    @Column(columnDefinition = "TEXT")
    private String rating;
    @Column(columnDefinition = "TEXT")
    private String webMaster;
}
