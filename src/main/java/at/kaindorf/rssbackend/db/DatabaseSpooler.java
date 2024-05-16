package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.RssChannel;
import at.kaindorf.rssbackend.pojos.RssItem;
import at.kaindorf.rssbackend.pojos.RssOuter;
import jakarta.annotation.PostConstruct;
import jakarta.xml.bind.JAXB;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Project: RSSBackend
 * Created by: eibmac20
 * Date: 09.04.24
 *
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseSpooler {

    private final String feedUrl = "https://www.kleinezeitung.at/rss/service/newsticker";

    private final RssChannelRepo rssChannelRepo;
    private final RssItemRepo rssItemRepo;

    @PostConstruct
    public void initializeSpooling() {
        loadData(feedUrl);
    }

    public void loadData(String url) {
        RssOuter rss = JAXB.unmarshal(url, RssOuter.class);
        RssChannel channel = rss.getChannel();
        rss.getChannel().getRssItems().forEach(s -> s.setRssChannel(channel));
        channel.setFeedUrl(url);
        if (!rssChannelRepo.existsByFeedUrl(url)) rssChannelRepo.save(rss.getChannel());
    }

    public List<RssItem> getItemsFromDB(String url) {
        RssChannel channel = rssChannelRepo.findRssChannelByFeedUrl(url);

        return channel.getRssItems();
    }

}
