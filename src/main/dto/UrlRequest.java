package jonathan.link_shortener.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class UrlRequest {
    @NotBlank
    @URL
    private String longUrl;
}