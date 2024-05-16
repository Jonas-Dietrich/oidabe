package at.kaindorf.rssbackend.pojos;

import at.kaindorf.rssbackend.util.LocalDateConverter;
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
public class RssItem {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Exclude
    private Long item_id;

    private String title;
    private String link;
    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @XmlElement(name = "channel")
    @ToString.Exclude
    @JsonIgnore
    private RssChannel rssChannel;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @XmlElement(name = "enclosure")
    private RssEnclosureURL enclosureURL;

    @XmlJavaTypeAdapter(LocalDateConverter.class)
    private LocalDateTime pubDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private RssCategory category;

    private String author;
    private String comments;
    private String guid;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private RssSource source;


    public String toString() {
        return String.format("%d: %s, %s", item_id, title, link);
    }
}
