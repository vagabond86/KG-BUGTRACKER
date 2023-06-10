package wsb.bugtracker.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import wsb.bugtracker.models.Authority;
import wsb.bugtracker.models.Person;
import wsb.bugtracker.repositories.AuthorityRepository;
import wsb.bugtracker.repositories.PersonRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthorityRepository authorityRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    public void save(Person person) {
        personRepository.save(person);
    }

    public Person findById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Value("${admin.login}")
    private String adminLogin;
    @Value("${admin.password}")
    private String adminPassword;
    @Value("${admin.email}")
    private String adminEmail;

    public void saveAdmin() {
        String login = adminLogin;
        String password = adminPassword;
        String email = adminEmail;

        Optional<Person> person = personRepository.findByLogin(login);

        if (person.isPresent()) {
            System.out.println("Użytkownik administracyjny już istnieje!");
            saveAllAuthorities(person.get());
            return;
        }

        System.out.println("Tworzę użytkownika administracyjnego!");

        Person newPerson = new Person();
        newPerson.setLogin(login);
        newPerson.setUserRealName(login);
        newPerson.setEmail(email);

        String hashedPassword = bCryptPasswordEncoder.encode(password);
        newPerson.setPassword(hashedPassword);

        personRepository.save(newPerson);

        saveAllAuthorities(newPerson);
    }

    public void saveAllAuthorities(Person person) {

        List<Authority> authorities = authorityRepository.findAll();
        Set<Authority> authoritySet = new HashSet<>(authorities);

        person.setAuthorities(authoritySet);

        personRepository.save(person);


    }
}
