package eu.mrndesign.www.matned.controller;

import eu.mrndesign.www.matned.service.PersonService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }
}
