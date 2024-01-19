/**
 * Used for tranfering Account informations to HTTP CLient
 */
package info.patriceallary.chatop.domain.dto;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class UserDto {

    private static final DateTimeFormatter formater = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

    private Integer id;
    private String name;
    private String email;
    private String createdAt;
    private String updatedAt;

    public UserDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = formater.format(createdAt.toLocalDateTime());
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt =  updatedAt != null ?  formater.format(updatedAt.toLocalDateTime()) : "";
    }
}
