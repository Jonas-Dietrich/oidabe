package at.kaindorf.rssbackend.pojos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Project: RSSBackend
 * Created by: eibmac20
 * Date: 09.04.24
 * The RssImage class represents an image in an RSS feed.
 * It is annotated as an Entity, meaning it is a JPA entity and is mapped to a database table.
 * The class uses Lombok annotations for automatic generation of getters, setters, equals, hashCode and toString methods.
 * It also uses JAXB annotations for defining XML marshalling behaviour.
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD) // so löst man das problem
public class RssImage {
    @GeneratedValue
    @Id
    @EqualsAndHashCode.Exclude
    @XmlTransient
    private Long imageId;

    /**
     * This is a constructor for the RssImage class.
     * It initializes a new RssImage object with the provided URL and title.
     *
     * @param url The URL of the image.
     * @param title The title of the image.
     */
    public RssImage(String url, String title) {
        this.url = url;
        this.title = title;
    }

    @Column(columnDefinition = "TEXT")
    private String url;

    @Column(columnDefinition = "TEXT")
    private String link;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Short height = 31;
    private Short width = 88;
    @Column(columnDefinition = "TEXT")
    private String title;
}
