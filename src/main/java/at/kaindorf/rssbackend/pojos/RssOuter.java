package at.kaindorf.rssbackend.pojos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project: RSSBackend
 * Created by: diejoc20
 * Date: 03.05.24
 * The RssOuter class represents the outer structure of an RSS feed.
 * It is annotated with JAXB annotations to define its XML marshalling behaviour.
 * The class uses Lombok annotations.
 */
@Data
@XmlRootElement
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class RssOuter {
    private RssChannel channel;
    private Double version;
}
