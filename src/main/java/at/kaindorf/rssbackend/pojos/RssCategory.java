package at.kaindorf.rssbackend.pojos;

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
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RssCategory {
    @Id
    @XmlTransient
    @GeneratedValue
    private Long categoryId;

    @XmlValue
    @EqualsAndHashCode.Include
    private String categoryName;

    @XmlAttribute
    @EqualsAndHashCode.Include
    private String domain;
}
