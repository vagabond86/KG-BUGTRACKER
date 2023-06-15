package wsb.bugtracker.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
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

        return Specification.allOf(
                equalTo("enabled", true),
                ilike("name", name),
                equalTo("creator", creator)
        ).and(Specification.anyOf(
                ilike("name", globalSearch),
                ilike("description", globalSearch)
        ));
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

    public String toQueryString(Integer page, Sort sort) {
        return "page=" + page +
                "&sort=" + toSortString(sort) +
                (name != null ? "&name=" + name : "") +
                (creator != null ? "&creator=" + creator.getId() : "") +
                (globalSearch != null ? "&globalSearch=" + globalSearch : "");
    }

    public String toSortString(Sort sort) {
        Sort.Order order = sort.getOrderFor("name");
        String sortString = "";
        if (order != null) {
            sortString += "name," + order.getDirection();
        }
        return sortString;
    }

    public Sort findNextSorting(Sort currentSorting) {
        Sort.Direction currentDirection = currentSorting.getOrderFor("name") != null ? currentSorting.getOrderFor("name").getDirection() : null;

        if (currentDirection == null) {
            return Sort.by("name").ascending();
        } else if (currentDirection.isAscending()) {
            return Sort.by("name").descending();
        } else {
            return Sort.unsorted();
        }

    }

}
