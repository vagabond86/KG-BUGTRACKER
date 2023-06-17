package wsb.bugtracker.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import wsb.bugtracker.models.Authority;
import wsb.bugtracker.models.AuthorityName;
import wsb.bugtracker.models.Person;
import wsb.bugtracker.repositories.AuthorityRepository;
import wsb.bugtracker.services.PersonService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/people")
public class AuthorityController {

    private final PersonService personService;
    private final AuthorityRepository authorityRepository;

    @GetMapping("/{id}/editAuthorities")
    public ModelAndView editAuthorities(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/authority/edit-authorities");
        List<Authority> authorities = authorityRepository.findAll();
        modelAndView.addObject("allAuthorities", authorities);

        Person person = personService.findById(id);
        modelAndView.addObject("person", person);
        return modelAndView;
    }

    @PostMapping("/{id}/saveAuthorities")
    public String saveAuthorities(@PathVariable Long id, @RequestParam(value = "authorities", required = false) List<AuthorityName> authorityNames) {
        Person person = personService.findById(id);
        Set<Authority> selectedAuthorities = new HashSet<>();
        if (authorityNames != null) {
            for (AuthorityName authorityName : authorityNames) {
                Authority authority = authorityRepository.findByName(authorityName).orElseThrow(() -> new RuntimeException("Authority not found: " + authorityName));
                selectedAuthorities.add(authority);
            }
        }
        person.setAuthorities(selectedAuthorities);
        personService.save(person);
        return "redirect:/people";
    }
}
