package wsb.bugtracker.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import wsb.bugtracker.models.Person;
import wsb.bugtracker.models.Project;
import wsb.bugtracker.services.PersonService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/people")
public class PersonController {

    final private PersonService personService;

    @GetMapping
    ModelAndView list () {
        ModelAndView modelAndView = new ModelAndView("/people/list");
        List<Person> people = personService.findAll();
        modelAndView.addObject("people", people);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    ModelAndView delete(@PathVariable Long id) {
        System.out.println("usuwanie użytkownika" + id);
        personService.delete(id);
        return new ModelAndView("redirect:/people");
    }
}
