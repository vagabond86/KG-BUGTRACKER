package wsb.bugtracker.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import wsb.bugtracker.models.Issue;
import wsb.bugtracker.models.Project;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    public void sendMail(Mail mail) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setTo(mail.getRecipient());
            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setText(mail.getContent());
            if (mail.getAttachment() != null) {
                mimeMessageHelper.addAttachment(mail.getAttachment().getOriginalFilename(), mail.getAttachment());
            }
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.out.println("Error sending. Try again later.");
        }
    }

    public void sendNewProjectMail(Project project) {
        Mail mail = new Mail();
        mail.setRecipient(project.getCreator().getEmail());
        mail.setSubject("New project on KG BUGTRACKER");
        mail.setContent("Confirmation of the creation of a new project: " + project.getName() + ", on " + project.getDateCreated());

        sendMail(mail);
    }

    public void sendEditProjectMail(Project project) {
        Mail mail = new Mail();
        mail.setRecipient(project.getCreator().getEmail());
        mail.setSubject("Edited project on KG BUGTRACKER");
        mail.setContent("Confirmation of project editing:" + project.getName());

        sendMail(mail);
    }

    public void sendDeleteProjectMail(Project project) {
        Mail mail = new Mail();
        mail.setRecipient(project.getCreator().getEmail());
        mail.setSubject("Deleted project on KG BUGTRACKER");
        mail.setContent("Confirmation of the removal of the project from the system: " + project.getName());

        sendMail(mail);
    }

    public void sendNewIssueMail(Issue issue) {
        Mail mail = new Mail();
        mail.setRecipient(issue.getCreator().getEmail());
        mail.setSubject("New issue on KG BUGTRACKER");
        mail.setContent("Confirmation of the creation of a new issue:\n\n" +
                "Priority: " + issue.getPriority() + "\n" +
                "Issue Name: " + issue.getName() + "\n" +
                "Description: " + issue.getDescription() + "\n" +
                "Type: " + issue.getType());

        sendMail(mail);
    }

    public void sendEditIssueMail(Issue issue) {
        Mail mail = new Mail();
        mail.setRecipient(issue.getCreator().getEmail());
        mail.setContent("Confirmation of issue editing:\n\n" +
                "Issue Name: " + issue.getName());

        sendMail(mail);
    }

    public void sendDeleteIssueMail(Issue issue) {
        Mail mail = new Mail();
        mail.setRecipient(issue.getCreator().getEmail());
        mail.setSubject("Deleted issue on KG BUGTRACKER");
        mail.setContent("Confirmation of the removal of the issue from the system:\n\n" +
                "Issue Name: " + issue.getName());

        sendMail(mail);
    }

}
