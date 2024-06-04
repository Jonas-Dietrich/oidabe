package at.kaindorf.rssbackend.pojos;

import at.kaindorf.rssbackend.util.LocalDateConverter;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Project: RSSBackend
 * Created by: diejoc20
 * Date: 09.04.24
 * The RssItem class represents an item in an RSS feed.
 * The class uses Lombok annotations for automatic generation of getters, setters, equals, hashCode and toString methods.
 * It also uses JAXB annotations for defining XML marshalling behaviour.
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD) // so löst man das problem
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RssItem {
    @Id
    @GeneratedValue
    @JsonAlias("item_id")
    private Long itemId;

    @Column(columnDefinition = "TEXT")
    @EqualsAndHashCode.Include
    private String link;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String title;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @XmlElement(name = "channel")
    @ToString.Exclude
    @JsonIgnore
    @EqualsAndHashCode.Include
    private RssChannel rssChannel;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @XmlElement(name = "enclosure")
    private RssEnclosureURL enclosureURL;

    @XmlJavaTypeAdapter(LocalDateConverter.class)
    private LocalDateTime pubDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private RssCategory category;

    @Column(columnDefinition = "TEXT")
    private String author;
    @Column(columnDefinition = "TEXT")
    private String comments;
    @Column(columnDefinition = "TEXT")
    private String guid;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private RssSource source;
}
