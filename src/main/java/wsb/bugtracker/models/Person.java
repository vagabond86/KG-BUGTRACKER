package wsb.bugtracker.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    @NotEmpty
    private String login;

    @Column(nullable = false)
    @NotEmpty
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W)[A-Za-z\\d\\W]{8,}$")
    private String password;

    @Column(nullable = false)
    @NotEmpty
    private String userRealName;

    @Email
    @NotEmpty
    private String email;

    @ManyToMany
    @JoinTable(name = "person_authorities",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities;
}