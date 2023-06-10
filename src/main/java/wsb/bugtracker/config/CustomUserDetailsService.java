package wsb.bugtracker.config;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wsb.bugtracker.models.Authority;
import wsb.bugtracker.models.Person;
import wsb.bugtracker.repositories.PersonRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByLogin(username).orElse(null);

        if (person == null) {
            throw new UsernameNotFoundException(username);
        }
        Set<GrantedAuthority> authorities = findAuthorities(person);

        return new User(username, person.getPassword(), authorities);
    }

    private Set<GrantedAuthority> findAuthorities(Person person) {
        Set<GrantedAuthority> authoritySet = new HashSet<>();

        for (Authority a : person.getAuthorities()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(a.getName().name());
            authoritySet.add(grantedAuthority);
        }
        return authoritySet;

    }
}
