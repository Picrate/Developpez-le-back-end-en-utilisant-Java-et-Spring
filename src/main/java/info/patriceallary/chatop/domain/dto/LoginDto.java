/**
 * Login datas transfert Object
 */
package info.patriceallary.chatop.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDto {

    @NotBlank
    @Email
    private String login;

    @NotBlank
    private String password;
}
