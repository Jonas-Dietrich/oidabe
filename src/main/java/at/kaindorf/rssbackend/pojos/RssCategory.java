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
 * The RssCategory class represents a single RSS feed category.
 * It includes details about the category such as the category ID, category name, and domain.
 * This class is also a JPA entity, meaning instances of this class can be automatically persisted in a database.
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class RssCategory {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Exclude
    @XmlTransient
    private Long rssCategoryId;

    @XmlValue
    @Column(columnDefinition = "TEXT")
    private String categoryName;

    @XmlAttribute
    @Column(columnDefinition = "TEXT")
    private String domain;
}
