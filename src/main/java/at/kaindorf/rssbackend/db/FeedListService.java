package at.kaindorf.rssbackend.db;

import at.kaindorf.rssbackend.pojos.RssChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Project: RssBackend
 * Created by: eibmac20
 * Date: 16.05.24
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class FeedListService {
    private final RssChannelRepo rssChannelRepo;
    private final InitDatabase initDatabase;


    public List<RssChannel> getChannels() throws Exception {
        return rssChannelRepo.findAll();
    }

    public List<RssChannel> getChannels(List<String> urls) throws Exception {
        for (String url : urls) {
            initDatabase.loadData(url);
        }
        return getChannels().stream().filter(c -> urls.contains(c.getFeedUrl())).collect(Collectors.toList());
    }
}
