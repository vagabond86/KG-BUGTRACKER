package wsb.bugtracker.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    private String name;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Column(nullable = false)
    private Date dateCreated;

    @Column(columnDefinition = "TEXT")
    @NotNull
    @NotEmpty
    @Size(min = 10, max = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private Person creator;
}
