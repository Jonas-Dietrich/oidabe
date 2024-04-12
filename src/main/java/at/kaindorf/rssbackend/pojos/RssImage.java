package at.kaindorf.rssbackend.pojos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Project: RSSBackend
 * Created by: eibmac20
 * Date: 09.04.24
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD) // so löst man das problem
public class RssImage {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Exclude
    private Long image_id;

    private String url;
    private String link;
    private String description;
    private Short height = 31;
    private Short width = 88;
    private String title;
}
