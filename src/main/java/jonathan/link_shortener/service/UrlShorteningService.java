package jonathan.link_shortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import jonathan.link_shortener.model.URLmapping;
import jonathan.link_shortener.repository.UrlMappingRepository;

@Service
public class UrlShorteningService {
    public static final String BASE_62_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Autowired
    private UrlMappingRepository repository;

    public String shortenUrl(String longUrl) {
        // Check if the long URL already exists
        Optional<URLmapping> existingMapping = repository.findByLongUrl(longUrl);
        if (existingMapping.isPresent()) {
            // If it exists, return the existing short code
            return existingMapping.get().getShortCode();
        } else {
            // If not, create a new entry
            URLmapping newUrlMapping = new URLmapping();
            newUrlMapping.setLongUrl(longUrl);
            URLmapping savedEntity = repository.save(newUrlMapping);

            String shortCode = toBase62(savedEntity.getId());
            savedEntity.setShortCode(shortCode);
            repository.save(savedEntity);

            return shortCode;
        }
    }

    public Optional<String> getLongUrl(String shortCode) {
        return repository.findByShortCode(shortCode)
                        .map(URLmapping::getLongUrl);
    }

    private String toBase62(long id) {
        if (id == 0) return String.valueOf(BASE_62_CHARS.charAt(0));

        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            sb.append(BASE_62_CHARS.charAt((int) (id % BASE_62_CHARS.length())));
            id /= BASE_62_CHARS.length();
        }
        return sb.reverse().toString();
    }
}
