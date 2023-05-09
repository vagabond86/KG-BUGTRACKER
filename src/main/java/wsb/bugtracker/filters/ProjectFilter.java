package wsb.bugtracker.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wsb.bugtracker.models.Person;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectFilter {

    private String name;

    private Person creator;

    private String globalSearch;
}
