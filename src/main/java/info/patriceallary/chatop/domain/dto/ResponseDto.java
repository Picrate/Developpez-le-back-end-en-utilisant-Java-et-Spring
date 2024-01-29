package info.patriceallary.chatop.domain.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {

    String message = "";

    public ResponseDto(String message) {
        this.message = message;
    }
}
