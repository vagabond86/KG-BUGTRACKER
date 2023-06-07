package wsb.bugtracker.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import wsb.bugtracker.services.PersonService;

@AllArgsConstructor
@Service
public class Bootstrap implements InitializingBean {

    PersonService personService;
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Rozpoczynamy!");

        personService.saveAdmin();
    }

}
