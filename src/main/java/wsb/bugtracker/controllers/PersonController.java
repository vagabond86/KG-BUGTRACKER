package wsb.bugtracker.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import wsb.bugtracker.models.Person;
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

    @GetMapping("/create")
    ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("/people/create");
        Person newPerson = new Person();
        modelAndView.addObject("person", newPerson);

        return modelAndView;
    }

    @PostMapping("/save")
    ModelAndView save(@ModelAttribute @Valid Person person, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()) {
            modelAndView.setViewName("people/create");
            modelAndView.addObject("person", person);
            return modelAndView;
        }

        personService.save(person);
        modelAndView.setViewName("redirect:/people");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/people/edit");
        Person person = personService.findById(id);
        modelAndView.addObject("person", person);
        return modelAndView;
    }

    @PostMapping("/update")
    ModelAndView update(@ModelAttribute @Valid Person person, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()) {
            modelAndView.setViewName("people/edit");
            modelAndView.addObject("person", person);
            return modelAndView;
        }

        personService.save(person);
        modelAndView.setViewName("redirect:/people");
        return modelAndView;
    }

}
