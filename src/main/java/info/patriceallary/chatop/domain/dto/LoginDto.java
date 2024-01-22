/**
 * Login datas transfert Object
 */
package info.patriceallary.chatop.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDto {

    @NotBlank
    @Email
    @Max(255)
    private String login;

    @NotBlank
    @Max(255)
    private String password;
}
