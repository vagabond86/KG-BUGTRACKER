package wsb.bugtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(securedEnabled = true)
public class WsbDemoBugtrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsbDemoBugtrackerApplication.class, args);
    }

}
