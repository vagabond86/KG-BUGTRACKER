package wsb.bugtracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wsb.bugtracker.models.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long> {
}
