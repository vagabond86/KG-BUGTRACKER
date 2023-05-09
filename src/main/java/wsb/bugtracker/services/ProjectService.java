package wsb.bugtracker.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import wsb.bugtracker.models.Project;
import wsb.bugtracker.repositories.ProjectRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    final private ProjectRepository projectRepository;


    public Page<Project> findAll(Specification<Project> specification, Pageable pageable) {
        return projectRepository.findAll(specification, pageable);


    }
}

