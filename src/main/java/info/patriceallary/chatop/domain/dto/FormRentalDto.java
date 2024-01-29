package info.patriceallary.chatop.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FormRentalDto {
    String name;
    String surface;
    String price;
    String description;
    MultipartFile picture;
}
