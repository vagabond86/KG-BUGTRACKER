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

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/people")
public class AuthorityController {

    private final PersonService personService;
    private final AuthorityRepository authorityRepository;

    @GetMapping("/{id}/authorityList")
    public ModelAndView creatorAuthorityList(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/authority/creator-authority-list");
        List<Authority> authorities = authorityRepository.findAll();
        modelAndView.addObject("authorities", authorities);

        Person person = personService.findById(id);
        modelAndView.addObject("person", person);
        return modelAndView;
    }

    @PostMapping("/{id}/addAuthority")
    public ModelAndView addAuthority(@PathVariable Long id, @RequestParam String authorityName) {
        ModelAndView modelAndView = new ModelAndView();
        Person person = personService.findById(id);

        if (person == null) {
            modelAndView.setViewName("redirect:/people");
            return modelAndView;
        }

        Optional<Authority> authorityOptional = authorityRepository.findByName(AuthorityName.valueOf(authorityName));
        if (authorityOptional.isEmpty()) {
            Authority authority = new Authority();
            authority.setName(AuthorityName.valueOf(authorityName));
            authorityRepository.save(authority);
            person.getAuthorities().add(authority);
            personService.save(person);
        }

        modelAndView.setViewName("redirect:/people");
        return modelAndView;
    }

    @PostMapping("/{personId}/delete/{authorityId}")
    public ModelAndView deleteAuthority(@PathVariable Long personId, @PathVariable Long authorityId) {
        ModelAndView modelAndView = new ModelAndView();
        Person person = personService.findById(personId);
        Optional<Authority> authorityOptional = authorityRepository.findById(authorityId);

        if (person == null || authorityOptional.isEmpty()) {
            modelAndView.setViewName("redirect:/people");
            return modelAndView;
        }

        Authority authority = authorityOptional.get();
        person.getAuthorities().remove(authority);
        personService.save(person);

        authorityRepository.delete(authority);

        modelAndView.setViewName("redirect:/people");
        return modelAndView;
    }
}
