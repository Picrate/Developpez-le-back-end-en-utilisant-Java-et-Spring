/**
 * User Account Entity
 */
package info.patriceallary.chatop.domain.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Transactional
@Table(name = "USERS")
@Getter
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Email
    @NotBlank
    private String email;

    @Setter
    @NotBlank
    private String name;

    @Setter
    @NotBlank
    private String password;

    @Setter
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Setter
    @Column(name="updated_at")
    private Timestamp updatedAt;


    @Getter
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @JoinTable(
            name = "USERS_ROLES",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();


    @Getter
    @OneToMany(mappedBy = "user")
    private final List<Message> messages = new ArrayList<>();

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.createdAt = Timestamp.valueOf(LocalDate.now().atStartOfDay());
    }

    protected User() {}

    // Roles Helper Functions
    public void addRole(Role role) {
        this.roles.add(role);
    }
    public void removeRole(Role role) {
        this.roles.remove(role);
    }

}