package info.patriceallary.chatop.domain.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageDto {

    @NotBlank
    @Size(min = 1, max = 2000)
    private String message;

    @NotEmpty
    @Positive
    private Integer user_id;

    @NotEmpty
    @Positive
    private Integer rental_id;

}
