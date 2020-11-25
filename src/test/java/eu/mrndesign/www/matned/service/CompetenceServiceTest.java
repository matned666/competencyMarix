package eu.mrndesign.www.matned.service;

import eu.mrndesign.www.matned.dto.CompetenceDTO;
import eu.mrndesign.www.matned.dto.PersonCompetenceDTO;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import eu.mrndesign.www.matned.model.personal.Competence;
import eu.mrndesign.www.matned.model.personal.Person;
import eu.mrndesign.www.matned.model.personal.PersonCompetence;
import eu.mrndesign.www.matned.repository.CompetenceRepository;
import eu.mrndesign.www.matned.repository.PersonCompetenceRepository;
import eu.mrndesign.www.matned.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith({SpringExtension.class})
@SpringBootTest
class CompetenceServiceTest {

    @Autowired
    private CompetenceService competenceService;

    @MockBean
    private CompetenceRepository competenceRepository;
    @MockBean
    private PersonCompetenceRepository personCompetenceRepository;
    @MockBean
    private PersonService personService;

    @Test
    void successfulAddOfNewCompetence(){
        Competence competence = new Competence(new EntityDescription("TestName", "TestDescription"));
        doReturn(competence).when(competenceRepository).save(any());

        CompetenceDTO competenceDTO = CompetenceDTO.apply(competence);

        assertEquals(competenceService.addCompetence(competenceDTO), competenceDTO);

    }

    @Test
    void successfulUpdateOfCompetence(){
        Competence competence = new Competence(new EntityDescription("TestName", "TestDescription"));
        doReturn(Optional.of(competence)).when(competenceRepository).findById(any());


        CompetenceDTO competenceDTO = new CompetenceDTO("name", "description");
        doReturn(competence).when(competenceRepository).save(any());
        CompetenceDTO competenceDTO1 = competenceService.updateCompetence(0L, competenceDTO);

        assertEquals(competenceDTO1.getName(), "name");
    }

    @Test
    void successfulDeleteOfCompetence(){
        doNothing().when(competenceRepository).delete(any());

        assertDoesNotThrow(() -> competenceService.deleteCompetence(1L));
    }

    @Test
    void successfulShowAllCompetencesAsDTO(){
        List<Competence> competences =
                Collections.singletonList(new Competence(new EntityDescription("TestName", "TestDescription")));
        Page<Competence> competencePage = new PageImpl<>(competences);
        String[] sortBy = {"aa"};
        doReturn(competencePage).when(competenceRepository).findAll(competenceService.getPageable(0, 2, sortBy));
        assertEquals(competences.stream()
                .map(CompetenceDTO::apply)
                .collect(Collectors.toList()), competenceService.findAll(0,2, sortBy));

    }

    @Test
    void successfulShowCompetenceById(){
        Competence competence = new Competence(new EntityDescription("TestName", "TestDescription"));
        doReturn(Optional.of(competence)).when(competenceRepository).findById(any());

        assertEquals(CompetenceDTO.apply(competence), competenceService.findCompetenceById(1L));

    }

    @Test
    void successfulAddPersonCompetence(){
        Person person = new Person("John", "Smith");
        Competence competence = new Competence(new EntityDescription("Welder", "Welding steel"));

        PersonCompetence personCompetence = new PersonCompetence(2, person, competence);

        doReturn(personCompetence).when(personCompetenceRepository).save(any());
        doReturn(person).when(personService).findPersonByIdService(any());
        doReturn(Optional.of(competence)).when(competenceRepository).findById(any());
        assertEquals(PersonCompetenceDTO.apply(personCompetence), competenceService.addPersonCompetence(2, 0L, 0L));
    }

    @Test
    void successfulUpdatePersonComptence(){

    }

    @Test
    void updatePersonCompetenceButNothingChangedDueToTheSameCompetenceAdded(){

    }

    @Test
    void deletePersonCompetence(){

    }

    @Test
    void upgradePersonCompetence(){

    }

    @Test
    void downgradePersonCompetence(){

    }

    @Test
    void failedPersonCompetenceUpgradeDueToMaxAlreadyReached(){

    }

    @Test
    void failedPersonCompetenceDowngradeDueToZeroAlreadyReached(){

    }







}
