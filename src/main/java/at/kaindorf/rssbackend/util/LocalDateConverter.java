package at.kaindorf.rssbackend.util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Project: RSSBackend
 * Created by: diejoc20
 * Date: 09.04.24
 * This class is a custom XmlAdapter used for converting LocalDateTime objects to and from XML strings.
 * It is used in the RSSBackend project.
 * The format used for the XML representation of LocalDateTime is "EEE, dd MMM yyyy HH:mm:ss zzz" in English locale.
 *
 * @see jakarta.xml.bind.annotation.adapters.XmlAdapter
 */
public class LocalDateConverter extends XmlAdapter<String, LocalDateTime> {
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);

    @Override
    public LocalDateTime unmarshal(String dateString) throws RuntimeException {
        if (dateString != null && !dateString.isBlank()) return LocalDateTime.parse(dateString, DTF);
        return null;
    }

    @Override
    public String marshal(LocalDateTime localDate) throws RuntimeException {
        if (localDate != null) return localDate.format(DTF);
        return null;
    }
}
