package wsb.bugtracker.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import wsb.bugtracker.models.Issue;
import wsb.bugtracker.models.Person;
import wsb.bugtracker.models.Project;
import wsb.bugtracker.repositories.PersonRepository;
import wsb.bugtracker.services.IssueService;
import wsb.bugtracker.services.ProjectService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final IssueService issueService;
    private final ProjectService projectService;
    private final PersonRepository personRepository;

    @GetMapping("/dashboard")
    public ModelAndView dashboard(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("dashboard");
        String username = authentication.getName();
        Person person = personRepository.findByLogin(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        modelAndView.addObject("userRealName", person.getUserRealName());

        List<Issue> issues = issueService.findByAssignee(person);
        modelAndView.addObject("issue", issues);

        List<Project> projects = projectService.findByCreator(person);
        modelAndView.addObject("project", projects);

        return modelAndView;
    }
}