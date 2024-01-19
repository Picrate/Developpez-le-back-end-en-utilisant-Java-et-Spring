/**
 * Login datas transfert Object
 */
package info.patriceallary.chatop.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDto {
    @NotEmpty
    @Email
    private String login;

    @NotEmpty
    private String password;
}
