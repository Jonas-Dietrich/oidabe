package at.kaindorf.rssbackend.pojos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Project: RSSBackend
 * Created by: diejoc20
 * Date: 09.04.24
 * The RssSource class represents the source of an RSS feed and is an optional feature that feeds may implement.
 * The class uses Lombok annotations.
 * It also uses JAXB annotations for defining XML marshalling behaviour.
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RssSource {
    @XmlValue
    @EqualsAndHashCode.Include
    @Column(columnDefinition = "TEXT")
    @Id
    private String sourceName;

    @XmlAttribute
    @EqualsAndHashCode.Include
    @Column(columnDefinition = "TEXT")
    private String url;
}
