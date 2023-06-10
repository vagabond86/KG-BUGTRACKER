package wsb.bugtracker.config;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wsb.bugtracker.models.Person;
import wsb.bugtracker.repositories.PersonRepository;

import java.util.Collections;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByLogin(username).orElse(null);

        if (person == null){
            throw new UsernameNotFoundException(username);
        }
        return new User(username, person.getPassword(), Collections.emptyList());
    }
}
