/**
 * Used for tranfering Account informations to HTTP CLient
 */
package info.patriceallary.chatop.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Getter
public class UserDto {

    private static final DateTimeFormatter formater = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

    @Setter
    @NotEmpty
    private Integer id;

    @Setter
    @NotEmpty
    private String name;

    @Setter
    @NotEmpty
    private String email;

    @NotEmpty
    private String createdAt;

    private String updatedAt;

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = formater.format(createdAt.toLocalDateTime());
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt =  updatedAt != null ?  formater.format(updatedAt.toLocalDateTime()) : "";
    }
}
