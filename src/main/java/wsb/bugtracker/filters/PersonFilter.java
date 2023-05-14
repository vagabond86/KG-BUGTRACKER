package wsb.bugtracker.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import wsb.bugtracker.models.Person;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonFilter {
    private Person creator;

    public Specification<Person> buildSpecification() {
        return Specification.where(equalTo("enabled", true))
                .and(equalTo("creator", creator));
    }

    private Specification<Person> equalTo(String property, Object value) {
        if (value == null) {
            return Specification.where(null);
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(property), value);
    }

    private Specification<Person> ilike(String property, String value) {
        if (value == null) {
            return Specification.where(null);
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(property)), "%" + value.toLowerCase() + "%");
    }
}
