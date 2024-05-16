package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.RssChannel;
import at.kaindorf.rssbackend.pojos.RssOuter;
import jakarta.annotation.PostConstruct;
import jakarta.xml.bind.JAXB;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Project: RSSBackend
 * Created by: eibmac20
 * Date: 09.04.24
 *
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class InitDatabase {
    private final RssChannelRepo rssChannelRepo;
    private final RssItemRepo rssItemRepo;

    public void loadData(String feedUrl) throws Exception {
        RssOuter rss = JAXB.unmarshal(feedUrl, RssOuter.class);
        RssChannel channel = rss.getChannel();
        rss.getChannel().getRssItems().forEach(s -> s.setRssChannel(channel));
        channel.setFeedUrl(feedUrl);
        if (!rssChannelRepo.existsByFeedUrl(feedUrl)) rssChannelRepo.save(rss.getChannel());
    }
}
