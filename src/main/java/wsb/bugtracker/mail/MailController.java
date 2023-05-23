package wsb.bugtracker.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @GetMapping()
    String index() {
        return "mail/new-message";
    }

    @PostMapping
    String sendMail(@ModelAttribute Mail mail) {
        mailService.sendMail(mail);
        return "mail/message-sent";
    }
}
