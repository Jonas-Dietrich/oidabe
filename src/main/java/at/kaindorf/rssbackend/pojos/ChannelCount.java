package at.kaindorf.rssbackend.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Project: RssBackend
 * Created by: eibmac20
 * Date: 14.06.24
 */

@Data
@AllArgsConstructor
public class ChannelCount {
    private RssChannel rssChannel;
    private Long count;
}
