package jonathan.link_shortener.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.Optional;

import jonathan.link_shortener.dto.UrlRequest;
import jonathan.link_shortener.service.UrlShorteningService;

@RestController
public class UrlController {

    @Autowired
    private UrlShorteningService urlService;

    @Value("${app.baseUrl}")
    private String baseUrl;

    // Endpoint to create a short URL
    @PostMapping("/shorten")
    public ResponseEntity<String> createShortUrl(@Valid @RequestBody UrlRequest request) {
        String shortCode = urlService.shortenUrl(request.getLongUrl());
        String shortUrl = baseUrl + shortCode;
        return ResponseEntity.ok(shortUrl);
    }

    // Endpoint to redirect to the original URL
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortCode) {
        Optional<String> longUrl = urlService.getLongUrl(shortCode);
        
        if (longUrl.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND)
                                 .location(URI.create(longUrl.get()))
                                 .build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}