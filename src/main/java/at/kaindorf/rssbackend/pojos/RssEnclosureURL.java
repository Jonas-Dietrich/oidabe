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
