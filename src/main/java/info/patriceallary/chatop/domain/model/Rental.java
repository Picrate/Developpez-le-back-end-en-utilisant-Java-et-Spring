package info.patriceallary.chatop.domain.model;

import jakarta.persistence.*;
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

    private String name;

    private Integer surface;

    private Integer price;

    private String picture;

    private String description;

    @ManyToOne()
    @Column(name = "owner_id", nullable = false)
    private User owner;

    private Timestamp created_at;

    private Timestamp updated_at;

    @OneToMany(mappedBy = "rental")
    private List<Message> messages = new ArrayList<>();

}
