package eu.mrndesign.www.matned.controller;

import eu.mrndesign.www.matned.dto.PersonDTO;
import eu.mrndesign.www.matned.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person")
    public List<PersonDTO> getAllPeopleList(@RequestParam(defaultValue = "${default.sort.by}", name = "sort") String[] sort,
                                            @RequestParam(defaultValue = "${default.page.start}", name = "page") Integer page,
                                            @RequestParam(defaultValue = "${default.page.size}", name = "amount") Integer amount,
                                            @RequestParam(defaultValue = "", name = "search") String searchBy,
                                            @RequestParam(defaultValue = "", name = "element") String searchElement){
        switch (searchBy){
            case "firstName": return personService.findByFirstName(searchElement, page, amount, sort);
            case "lastName": return personService.findByLastName(searchElement, page, amount, sort);
            default: return personService.findAll(page, amount, sort);
        }

    }

    @GetMapping("/person/{id}")
    public PersonDTO getSinglePersonById(@PathVariable Long id){
        return personService.findById(id);
    }

    @DeleteMapping("/person/{id}")
    public List<PersonDTO> deletePersonFromList(@PathVariable Long id,
                                                @RequestParam(defaultValue = "${default.sort.by}", name = "sort") String[] sort,
                                                @RequestParam(defaultValue = "${default.page.start}", name = "page") Integer page,
                                                @RequestParam(defaultValue = "${default.page.size}", name = "amount") Integer amount){
        personService.delete(id);
        return personService.findAll(page, amount, sort);
    }

    @PostMapping("/person")
    public PersonDTO addNewPerson(@RequestBody PersonDTO person){
        return personService.add(person);
    }

    @PostMapping("/person/{id}")
    public PersonDTO updatePersonData(@PathVariable Long id,
                                      @RequestBody PersonDTO updatedData){
        return personService.update(id, updatedData);
    }


}
