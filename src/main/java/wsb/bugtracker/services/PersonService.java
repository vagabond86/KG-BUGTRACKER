package wsb.bugtracker.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import wsb.bugtracker.models.Person;
import wsb.bugtracker.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    final private PersonRepository personRepository;
    final private BCryptPasswordEncoder bCryptPasswordEncoder;

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

    public void saveAdmin(){
        String login = "admin";
        String password = "#Admin12!";
        String email = "admin@wsb.pl";

        Optional<Person> person = personRepository.findByLogin(login);

        if(person.isPresent()){
            System.out.println();
            System.out.println("Użytkownik administracyjny już istnieje!");
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
    }
}
