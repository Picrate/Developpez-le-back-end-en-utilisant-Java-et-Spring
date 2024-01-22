package info.patriceallary.chatop.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "RENTALS")
public class Rental implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Max(255)
    private String name;

    @PositiveOrZero
    private Float surface;

    @PositiveOrZero
    private Float price;

    @Max(255)
    private String picture;

    @Max(2000)
    private String description;

    @ManyToOne()
    @JoinColumn(name = "owner_id", nullable = false)
    @NotEmpty
    private User owner;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at")
    private Timestamp updated_at;

    @OneToMany(mappedBy = "rental")
    private List<Message> messages = new ArrayList<>();

}
