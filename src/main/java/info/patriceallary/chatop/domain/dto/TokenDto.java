/**
 * BearerToken data transfert Object
 * This class is used for sending BearerToken to HTTP CLient
 */
package info.patriceallary.chatop.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenDto {
    private String token;

    public TokenDto(String token) {
        this.token = token;
    }
}
