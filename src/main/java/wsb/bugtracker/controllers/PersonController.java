package wsb.bugtracker.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final PersonService personService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping
    @Secured("ROLE_LIST_USER")
    ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("/people/list");
        List<Person> people = personService.findAll();
        modelAndView.addObject("people", people);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    @Secured("ROLE_DELETE_USER")
    ModelAndView delete(@PathVariable Long id) {
        System.out.println("usuwanie użytkownika" + id);
        personService.delete(id);
        return new ModelAndView("redirect:/people");
    }

    @GetMapping("/create")
    @Secured("ROLE_CREATE_USER")
    ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("/people/create");
        Person newPerson = new Person();
        modelAndView.addObject("person", newPerson);

        return modelAndView;
    }

    @PostMapping("/save")
    @Secured("ROLE_CREATE_USER")
    ModelAndView save(@ModelAttribute @Valid Person person, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()) {
            modelAndView.setViewName("people/create");
            modelAndView.addObject("person", person);
            return modelAndView;
        }
        String hashedPassword = bCryptPasswordEncoder.encode(person.getPassword());

        person.setPassword(hashedPassword);
        personService.save(person);
        modelAndView.setViewName("redirect:/people");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    @Secured("ROLE_EDIT_USER")
    ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/people/edit");
        Person person = personService.findById(id);
        modelAndView.addObject("person", person);
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    @Secured("ROLE_CREATE_USER")
    ModelAndView update(@ModelAttribute @Valid Person person, BindingResult result, @PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()) {
            modelAndView.setViewName("people/edit");
            modelAndView.addObject("person", person);
            return modelAndView;
        }

        Person editPerson = personService.findById(id);
        if (editPerson == null) {
            return new ModelAndView("redirect:/people");
        }

        editPerson.setEmail(person.getEmail());
        editPerson.setUserRealName(person.getUserRealName());
        editPerson.setLogin(person.getLogin());

        String hashedPassword = bCryptPasswordEncoder.encode(person.getPassword());
        editPerson.setPassword(hashedPassword);

        personService.save(editPerson);
        modelAndView.setViewName("redirect:/people");
        return modelAndView;
    }
}