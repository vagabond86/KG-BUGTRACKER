package wsb.bugtracker.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import wsb.bugtracker.models.Person;
import wsb.bugtracker.models.Project;
import wsb.bugtracker.services.PersonService;
import wsb.bugtracker.services.ProjectService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    final private ProjectService projectService;
    final private PersonService personService;

    @GetMapping
    ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("projects/index");
        List<Project> projects = projectService.findAll();
        modelAndView.addObject("projects", projects);
        List<Person> people = personService.findAll();
        modelAndView.addObject("people", people);
        return modelAndView;
    }

    @GetMapping("/test")
    ModelAndView test(){
        ModelAndView modelAndView = new ModelAndView("projects/test");
        List<Person> people = personService.findAll();
        modelAndView.addObject("people", people);
        return modelAndView;
    }
}