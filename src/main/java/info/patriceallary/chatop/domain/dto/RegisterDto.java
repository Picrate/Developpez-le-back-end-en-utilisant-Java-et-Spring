/**
 * Register informations datas transfert Object
 * This class is used for retrieving new account informations sent by HTTP client *
 */
package info.patriceallary.chatop.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterDto {

    @NotEmpty
    @Size(min = 1, max = 255)
    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 7, max = 255)
    private String password;

}
