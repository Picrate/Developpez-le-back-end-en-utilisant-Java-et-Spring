/**
 * Register informations datas transfert Object
 * This class is used for retrieving new account informations sent by HTTP client *
 */
package info.patriceallary.chatop.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterDto {

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;

}
