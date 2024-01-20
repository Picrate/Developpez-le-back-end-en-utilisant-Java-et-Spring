package info.patriceallary.chatop.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageDto {

    @NotBlank
    @Max(2000)
    private String message;

    @NotEmpty
    @Positive
    private Integer user_id;

    @NotEmpty
    @Positive
    private Integer rental_id;

}
