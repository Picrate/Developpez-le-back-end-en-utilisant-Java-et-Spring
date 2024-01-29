/**
 * Used for tranfering Account informations to HTTP CLient
 */
package info.patriceallary.chatop.domain.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private static final String VALID_DATE_PATTERN = "^\\d{2}/\\d{2}/\\d{4}$";

    @NotEmpty
    private Integer id;

    @NotBlank
    @Max(255)
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = VALID_DATE_PATTERN)
    private String createdAt;

    @Pattern(regexp = VALID_DATE_PATTERN)
    private String updatedAt;
}
