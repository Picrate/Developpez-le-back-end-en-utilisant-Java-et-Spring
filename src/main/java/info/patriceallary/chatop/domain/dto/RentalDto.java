package info.patriceallary.chatop.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalDto {

    private static final String VALID_DATE_PATTERN = "^\\d{2}/\\d{2}/\\d{4}$";

    @Positive
    private Integer id;

    @NotBlank
    private String name;

    @Positive
    @Max(Integer.MAX_VALUE)
    private Integer surface;

    @Positive
    @Max(Integer.MAX_VALUE)
    private Integer price;

    private String picture;

    private String description;

    private Integer owner_id;

    @NotBlank
    @Pattern(regexp = VALID_DATE_PATTERN)
    private String created_at;

    @Pattern(regexp = VALID_DATE_PATTERN)
    private String updated_at;

}
