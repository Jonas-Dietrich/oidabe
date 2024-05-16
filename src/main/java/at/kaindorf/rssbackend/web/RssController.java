package at.kaindorf.rssbackend.web;

import at.kaindorf.rssbackend.db.DatabaseSpooler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Project: RSSBackend
 * Created by: diejoc20
 * Date: 03.05.24
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/rssfeed/api")
@CrossOrigin("*")
public class RssController {
    private final DatabaseSpooler db;
}
