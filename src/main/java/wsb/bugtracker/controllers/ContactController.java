package wsb.bugtracker.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class ContactController {

    @GetMapping("/contact")
    public ModelAndView contact() {
        boolean loggedIn = isLoggedIn();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("loggedIn", loggedIn);
        return modelAndView;
    }

    private boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }
}