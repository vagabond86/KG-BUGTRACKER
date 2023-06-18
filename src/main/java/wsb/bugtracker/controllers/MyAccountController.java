package wsb.bugtracker.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import wsb.bugtracker.models.Person;
import wsb.bugtracker.repositories.PersonRepository;
import wsb.bugtracker.services.PersonService;


@Controller
@RequiredArgsConstructor
@RequestMapping("/my-account")
public class MyAccountController {

    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PersonService personService;

    @GetMapping
    @Secured("ROLE_USER")
    ModelAndView myAccount(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("/my-account/my-account");
        String username = authentication.getName();
        Person person = personRepository.findByLogin(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        modelAndView.addObject("userRealName", person.getUserRealName());
        modelAndView.addObject("login", person.getLogin());
        modelAndView.addObject("email", person.getEmail());
        return modelAndView;
    }

    @GetMapping("/edit")
    @Secured("ROLE_USER")
    ModelAndView changePersonalData(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("/my-account/edit");
        String username = authentication.getName();
        Person person = personRepository.findByLogin(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        modelAndView.addObject("person", person);
        return modelAndView;
    }
}