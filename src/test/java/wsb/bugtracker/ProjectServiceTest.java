package wsb.bugtracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import wsb.bugtracker.models.Project;
import wsb.bugtracker.repositories.ProjectRepository;
import wsb.bugtracker.services.ProjectService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Sprawdzam, czy metoda findAll() zwraca poprawną listę wszystkich projektów.
    @Test
    void findAll_ShouldReturnAllProjects() {

        // Arrange
        List<Project> projects = new ArrayList<>();
        projects.add(new Project());
        projects.add(new Project());
        when(projectRepository.findAll()).thenReturn(projects);

        // Act
        List<Project> result = projectService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(projectRepository, times(1)).findAll();
    }

    // Sprawdzam, czy metoda findById() zwraca poprawny projekt dla istniejącego id.
    @Test
    void findById_ExistingId_ShouldReturnProject() {

        // Arrange
        Long projectId = 1L;
        Project project = new Project();
        project.setId(projectId);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // Act
        Project result = projectService.findById(projectId);

        // Assert
        assertEquals(projectId, result.getId());
        verify(projectRepository, times(1)).findById(projectId);
    }

    // Sprawdzam, czy metoda findById() zwraca null dla nieistniejącego id.
    @Test
    void findById_NonExistingId_ShouldReturnNull() {

        // Arrange
        Long projectId = 1L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // Act
        Project result = projectService.findById(projectId);

        // Assert
        assertEquals(null, result);
        verify(projectRepository, times(1)).findById(projectId);
    }

    // Sprawdzam, czy metoda save() ustawia poprawną datę utworzenia projektu i zapisuje go.
    @Test
    void save_NewProject_ShouldSetDateCreatedAndSaveProject() {

        // Arrange
        Project project = new Project();

        // Act
        projectService.save(project);

        // Assert
        assertEquals(true, project.getEnabled());
        assertEquals(true, project.getDateCreated() != null);
        verify(projectRepository, times(1)).save(project);
    }

    // Sprawdzam, czy metoda delete() usuwa projekt o podanym id.
    @Test
    void delete_ExistingId_ShouldDeleteProject() {

        // Arrange
        Long projectId = 1L;

        // Act
        projectService.delete(projectId);

        // Assert
        verify(projectRepository, times(1)).deleteById(projectId);
    }
}
