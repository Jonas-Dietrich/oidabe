package at.kaindorf.rssbackend.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Project: RssBackend
 * Created by: eibmac20
 * Date: 07.06.24
 */

@AllArgsConstructor
@Data
public class ApiAboutStats {
    private Long channelCount;
    private Long commentCount;
    private Long itemCount;
    private Long categoryCount;
    private Long sourceCount;
    private Long enclosureUrlCount;
}
