package info.patriceallary.chatop.domain.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalDto {

    private static final String VALID_DATE_PATTERN = "^\\d{2}/\\d{2}/\\d{4}$";

    @Positive
    private Integer id;

    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @NotEmpty
    @Positive
    @Max(Integer.MAX_VALUE)
    private Integer surface;

    @NotEmpty
    @Positive
    @Max(Integer.MAX_VALUE)
    private Integer price;

    @NotBlank
    private String picture;

    @NotBlank
    @Size(max = 2000)
    private String description;

    @NotEmpty
    @Positive
    @Max(Integer.MAX_VALUE)
    private Integer owner_id;

    @NotBlank
    @Pattern(regexp = VALID_DATE_PATTERN)
    private String created_at;


    @Pattern(regexp = VALID_DATE_PATTERN)
    private String updated_at;

}
