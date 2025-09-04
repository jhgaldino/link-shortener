package jonathan.link_shortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jonathan.link_shortener.model.URLmapping;
import java.util.Optional;

@Repository
public interface UrlMappingRepository extends JpaRepository<URLmapping, Long> {
    Optional<URLmapping> findByShortCode(String shortCode);
    Optional<URLmapping> findByLongUrl(String longUrl);
}
