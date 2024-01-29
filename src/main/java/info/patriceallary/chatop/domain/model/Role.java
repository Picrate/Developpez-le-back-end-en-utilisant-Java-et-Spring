/**
 * ROLE Entity
 * This Entity represent an application role of au User.
 * It is used for user authorization.
 */
package info.patriceallary.chatop.domain.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Transactional
@Table(name = "ROLES")
@Getter
@Setter
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String name;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name="updated_at")
    private Timestamp updatedAt;

    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    protected Role() {
    }

    public Role(String name) {
        this.name = name;
        this.createdAt = Timestamp.valueOf(LocalDate.now().atStartOfDay());
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }


}
