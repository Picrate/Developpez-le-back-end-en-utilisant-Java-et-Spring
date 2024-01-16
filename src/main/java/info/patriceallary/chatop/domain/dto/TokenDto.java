/**
 * BearerToken data transfert Object
 * This class is used for sending BearerToken to HTTP CLient
 */
package info.patriceallary.chatop.domain.dto;

public class TokenDto {
    String token;

    public TokenDto() {
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
