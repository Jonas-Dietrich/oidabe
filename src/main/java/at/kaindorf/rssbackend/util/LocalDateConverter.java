package at.kaindorf.rssbackend.util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Project: RSSBackend
 * Created by: diejoc20
 * Date: 09.04.24
 */
public class LocalDateConverter extends XmlAdapter<String, LocalDateTime> {
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);

    @Override
    public LocalDateTime unmarshal(String dateString) throws Exception {
        if (dateString != null && !dateString.isBlank()) return LocalDateTime.parse(dateString, DTF);
        return null;
    }

    @Override
    public String marshal(LocalDateTime localDate) throws Exception {
        if (localDate != null) return localDate.format(DTF);
        return null;
    }
}
