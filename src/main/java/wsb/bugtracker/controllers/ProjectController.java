package wsb.bugtracker.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import wsb.bugtracker.filters.ProjectFilter;
import wsb.bugtracker.mail.MailService;
import wsb.bugtracker.models.Person;
import wsb.bugtracker.models.Project;
import wsb.bugtracker.services.PersonService;
import wsb.bugtracker.services.ProjectService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final PersonService personService;
    private final MailService mailService;

    @GetMapping
    @Secured("ROLE_VIEW_PROJECT")
    ModelAndView index(@ModelAttribute ProjectFilter filter, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("projects/index");
        Page<Project> projects = projectService.findAll(filter.buildSpecification(), pageable);
        modelAndView.addObject("projects", projects);
        List<Person> people = personService.findAll();
        modelAndView.addObject("people", people);
        modelAndView.addObject("filter", filter);
        return modelAndView;
    }

    @GetMapping("/create")
    @Secured("ROLE_CREATE_PROJECT")
    ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("/projects/create");

        Project newProject = new Project();
        newProject.setEnabled(true);
        modelAndView.addObject("project", newProject);

        List<Person> people = personService.findAll();
        modelAndView.addObject("people", people);

        return modelAndView;
    }

    @PostMapping("/save")
    @Secured("ROLE_CREATE_PROJECT")
    ModelAndView save(@ModelAttribute @Valid Project project, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()) {
            modelAndView.setViewName("projects/create");
            modelAndView.addObject("project", project);
            modelAndView.addObject("people", personService.findAll());
            return modelAndView;
        }
        projectService.save(project);

        mailService.sendNewProjectMail(project);

        modelAndView.setViewName("redirect:/projects");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    @Secured("ROLE_DELETE_PROJECT")
    ModelAndView delete(@PathVariable Long id) {
        Project deletedProject = projectService.findById(id);
        mailService.sendDeleteProjectMail(deletedProject);

        System.out.println("project removal" + id);
        projectService.delete(id);

        return new ModelAndView("redirect:/projects");
    }

    @GetMapping("/edit/{id}")
    @Secured("ROLE_EDIT_PROJECT")
    ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/projects/edit");
        Project project = projectService.findById(id);
        modelAndView.addObject("project", project);

        List<Person> people = personService.findAll();
        modelAndView.addObject("people", people);
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    @Secured("ROLE_CREATE_PROJECT")
    ModelAndView update(@ModelAttribute @Valid Project project, BindingResult result, @PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()) {
            modelAndView.setViewName("projects/edit");
            modelAndView.addObject("project", project);
            return modelAndView;
        }

        Project editProject = projectService.findById(id);
        if (editProject == null) {
            return new ModelAndView("redirect:/projects");
        }

        editProject.setName(project.getName());
        editProject.setDescription(project.getDescription());
        editProject.setCreator(project.getCreator());

        projectService.save(editProject);
        mailService.sendEditProjectMail(editProject);

        modelAndView.setViewName("redirect:/projects");
        return modelAndView;
    }
}

