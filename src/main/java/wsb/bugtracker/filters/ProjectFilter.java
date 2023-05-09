package wsb.bugtracker.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import wsb.bugtracker.models.Person;
import wsb.bugtracker.models.Project;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectFilter {

    private String name;

    private Person creator;

    private String globalSearch;

    public Specification<Project> buildSpecification() {
        Specification<Project> spec = Specification.where(null);
        spec = spec.and(equalTo("enabled", true));
        spec = spec.and(ilike("name", name));
        spec = spec.and(equalTo("creator", creator));

        Specification<Project> nameIlike = ilike("name", globalSearch);
        Specification<Project> descriptionIlike = ilike("description", globalSearch);

        Specification<Project> globalSearchCondition = nameIlike.or(descriptionIlike);
        spec = spec.and(globalSearchCondition);

        return spec;
    }

    private Specification<Project> equalTo(String property, Object value) {
        if (value == null) {
            return Specification.where(null);
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(property), value);
    }


    private Specification<Project> ilike(String property, String value) {
        if (value == null) {
            return Specification.where(null);
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(property)), "%" + value.toLowerCase() + "%");
    }

}
