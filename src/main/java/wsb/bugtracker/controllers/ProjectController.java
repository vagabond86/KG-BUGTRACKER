package wsb.bugtracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @GetMapping
    ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("projects/index");
        // TODO: dodać model do widoku
        return modelAndView;

    }

}
