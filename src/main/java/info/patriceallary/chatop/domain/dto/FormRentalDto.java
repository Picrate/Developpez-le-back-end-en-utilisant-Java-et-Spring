package info.patriceallary.chatop.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FormRentalDto {

    @NotBlank
    @Size(min = 1, max = 255)
    String name;

    @NotBlank
    String surface;

    @NotBlank
    String price;

    @NotBlank
    @Size(max = 2000)
    String description;

    @NotEmpty
    MultipartFile picture;

}
