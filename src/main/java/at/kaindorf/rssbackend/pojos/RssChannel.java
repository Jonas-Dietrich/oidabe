package at.kaindorf.rssbackend.pojos;

import at.kaindorf.rssbackend.util.LocalDateConverter;
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
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RssChannel {
    @EqualsAndHashCode.Include
    @Id
    private String feedUrl;

    private String title;
    private String link;
    @Column(columnDefinition = "TEXT")
    private String description;

    @XmlJavaTypeAdapter(LocalDateConverter.class)
    private LocalDateTime lastBuildDate;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @XmlElement(name = "image")
    private RssImage rssImage;

    @OneToMany(mappedBy = "rssChannel", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @XmlElement(name = "item")
    private List<RssItem> rssItems;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private RssCategory category;

    private String copyright;
    private String docs;
    private String generator;
    private String language;
    private String managingEditor;
    private String rating;
    private String webMaster;
}
