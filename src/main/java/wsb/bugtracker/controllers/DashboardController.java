package wsb.bugtracker.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final IssueService issueService;
    private final ProjectService projectService;
    private final PersonRepository personRepository;

    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView("dashboard");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<Person> person = personRepository.findByLogin(user.getUsername());
        if (person.isPresent()) {
            String userRealName = person.get().getUserRealName();
            modelAndView.addObject("userRealName", userRealName);
        }

        modelAndView.addObject("principal", user);

        List<Issue> issues = issueService.findAll();
        modelAndView.addObject("issue", issues);

        List<Project> projects = projectService.findAll();
        modelAndView.addObject("project", projects);

        return modelAndView;
    }
}
