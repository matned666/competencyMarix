package eu.mrndesign.www.matned.service;

import eu.mrndesign.www.matned.dto.PersonDTO;
import eu.mrndesign.www.matned.model.personal.Person;
import eu.mrndesign.www.matned.model.security.User;
import eu.mrndesign.www.matned.repository.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static eu.mrndesign.www.matned.service.PersonService.WRONG_DATA_GIVEN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith({SpringExtension.class})
@SpringBootTest
class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    private List<Person> people;

    @BeforeEach
    void setup(){
        people = new LinkedList<>();
        Person person;
        char a = 65;
        person = new Person("Z.TestName0","A.TestSurname0");
        person.setCreatedDate(LocalDateTime.now());
        person.setLastModifiedDate(LocalDateTime.now());
        person.setCreatedBy(new User("testCreator"));
        person.setLastModifiedBy(new User("testUpdater"));
        people.add(person);
        for (int i = 1; i <= 2; i++) {
            person = new Person(a+".TestName"+i,a+".TestSurname"+i);
            person.setCreatedDate(LocalDateTime.now());
            person.setLastModifiedDate(LocalDateTime.now());
            person.setCreatedBy(new User("testCreator"));
            person.setLastModifiedBy(new User("testUpdater"));
            people.add(person);
            a = (char) (a+i);
        }

    }

    @AfterEach
    void reset(){
        people.clear();
    }

    @Test
    void findingAllInventoryElements(){
        doReturn(people).when(personRepository).findAll();
        List<PersonDTO> people = personService.findAll();
        assertEquals(3, people.size());
    }

    @Test
    void returningPersonDTOOnAdd(){
        doReturn(people.get(0)).when(personRepository).save(any());

        PersonDTO dto = personService.add(new PersonDTO());
    }

    @Test
    void updatePerson(){
        doReturn(Optional.of(people.get(1))).when(personRepository).findById(any());
        doReturn(people.get(1)).when(personRepository).save(any());

        PersonDTO updateData = new PersonDTO("Charles", null, null);
        PersonDTO dto = personService.update(0L, updateData);

        assertEquals(people.get(1).getFirstName(), "Charles");
        assertEquals(people.get(1).getLastName(), "A.TestSurname1");
    }

    @Test
    void updatePersonNullObjectGivenThrowsExceptionWithMessage(){
       assertThrows(RuntimeException.class, ()->personService.update(0L, null), WRONG_DATA_GIVEN);
    }

    @Test
    void successfulDeleteReturnsTrueUnsuccessfulFalse(){
        doReturn(Optional.empty()).when(personRepository).findById(any());
        assertTrue(personService.delete(0L));
        doReturn(Optional.of(people.get(0))).when(personRepository).findById(any());
        assertFalse(personService.delete(0L));
    }

    @Test
    void sortingTestByFirstName(){
        doReturn(people).when(personRepository).findByFirstNameNotPrecise("testname", any());
        List<PersonDTO> sorted = personService.findByFirstName("testname", any(), any(), any());

        assertEquals(sorted.size(), 3);
    }


}
