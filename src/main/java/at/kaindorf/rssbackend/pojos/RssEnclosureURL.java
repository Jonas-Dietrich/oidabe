package at.kaindorf.rssbackend.pojos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Project: RSSBackend
 * Created by: diejoc20
 * Date: 12.04.24
 * The RssEnclosureURL class represents an enclosure URL in an RSS feed.
 * It is annotated as an Entity, meaning it is a JPA entity and is mapped to a database table.
 * The class uses Lombok annotations for automatic generation of getters, setters, equals, hashCode and toString methods.
 * It also uses JAXB annotations for defining XML marshalling behaviour.
*/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD) // so löst man das problem
public class RssEnclosureURL {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Exclude
    private Long url_id;

    @XmlAttribute(name = "url")
    @Column(columnDefinition = "TEXT")
    private String url;
    @XmlAttribute(name = "length")
    private String length;
    @XmlAttribute(name = "type")
    private String type;
}
