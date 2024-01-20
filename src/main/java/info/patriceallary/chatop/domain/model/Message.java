package info.patriceallary.chatop.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "MESSAGES")
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank()
    private String message;

    @NotEmpty()
    private Timestamp created_at;

    private Timestamp updated_at;

    @NotEmpty
    @ManyToOne()
    @Column(name = "user_id", nullable = false)
    private User user;

    @NotEmpty
    @ManyToOne()
    @Column(name = "rental_id", nullable = false)
    private Rental rental;

    protected Message() {
    }

    public Message(String message, User user, Rental rental) {
        this.message = message;
        this.user = user;
        this.rental = rental;
        this.created_at = Timestamp.valueOf(LocalDateTime.now());
    }
}
