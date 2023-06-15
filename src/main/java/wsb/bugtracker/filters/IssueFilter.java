package wsb.bugtracker.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import wsb.bugtracker.models.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueFilter {

    private String name;

    private Status status;

    private Priority priority;

    private Type type;

    private Person creator;

    private Person assignee;

    private Project project;

    private String globalSearch;

    public Specification<Issue> buildSpecification() {
        return Specification.allOf(
                equalTo("name", name),
                equalTo("status", status),
                equalTo("priority", priority),
                equalTo("type", type),
                equalTo("creator", creator),
                equalTo("assignee", assignee),
                equalTo("project", project)
        ).and(Specification.anyOf(
                ilike("name", globalSearch),
                ilike("description", globalSearch)
        ));
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
        Sort.Order order = currentSorting.getOrderFor("name");
        Sort.Direction currentDirection = order != null ? order.getDirection() : null;

        if (currentDirection == null) {
            return Sort.by("name").ascending();
        } else if (currentDirection.isAscending()) {
            return Sort.by("name").descending();
        } else {
            return Sort.unsorted();
        }
    }

    private Specification<Issue> equalTo(String property, Object value) {
        if (value == null) {
            return Specification.where(null);
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(property), value);
    }

    private Specification<Issue> ilike(String property, String value) {
        if (value == null) {
            return Specification.where(null);
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(property)), "%" + value.toLowerCase() + "%");
    }
}