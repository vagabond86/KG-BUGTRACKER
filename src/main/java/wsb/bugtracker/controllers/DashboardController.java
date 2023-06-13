package wsb.bugtracker.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import wsb.bugtracker.models.Issue;
import wsb.bugtracker.models.Project;
import wsb.bugtracker.services.IssueService;
import wsb.bugtracker.services.ProjectService;

import java.util.List;
@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final IssueService issueService;
    private final ProjectService projectService;

    @GetMapping("/dashboard")
    public ModelAndView dashboard(){
        ModelAndView modelAndView = new ModelAndView("dashboard");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        modelAndView.addObject("principal", principal);

        List<Issue> issues = issueService.findAll();
        modelAndView.addObject("issue", issues);

        List<Project> projects = projectService.findAll();
        modelAndView.addObject("project", projects);

        return modelAndView;
    }
}
