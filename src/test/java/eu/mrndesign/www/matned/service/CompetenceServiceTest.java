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
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static eu.mrndesign.www.matned.service.CompetenceService.PERSON_COMPETENCE_MAX_LEVEL;
import static eu.mrndesign.www.matned.service.CompetenceService.PERSON_COMPETENCE_MIN_LEVEL;
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
    void deletePersonCompetence(){
        doNothing().when(personCompetenceRepository).delete(any());

        assertDoesNotThrow(() -> competenceService.deletePersonCompetence(1L));
    }

    @Test
    void upgradePersonCompetence(){
        PersonCompetence personCompetence = new PersonCompetence(0, null, null);

        doReturn(Optional.of(personCompetence)).when(personCompetenceRepository).findById(any());
        doReturn(personCompetence).when(personCompetenceRepository).save(any());

        assertEquals(competenceService.upgradePersonCompetence(1L).getLevel() , 1);
    }

    @Test
    void downgradePersonCompetence(){
        PersonCompetence personCompetence = new PersonCompetence(1, null, null);

        doReturn(Optional.of(personCompetence)).when(personCompetenceRepository).findById(any());
        doReturn(personCompetence).when(personCompetenceRepository).save(any());

        assertEquals(competenceService.downgradePersonCompetence(1L).getLevel() , 0);
    }

    @Test
    void failedPersonCompetenceUpgradeDueToMaxAlreadyReached(){
        PersonCompetence personCompetence = new PersonCompetence(PERSON_COMPETENCE_MAX_LEVEL, null, null);

        doReturn(Optional.of(personCompetence)).when(personCompetenceRepository).findById(any());
        doReturn(personCompetence).when(personCompetenceRepository).save(any());

        assertEquals(competenceService.upgradePersonCompetence(1L).getLevel() , PERSON_COMPETENCE_MAX_LEVEL);
    }

    @Test
    void failedPersonCompetenceDowngradeDueToZeroAlreadyReached(){
        PersonCompetence personCompetence = new PersonCompetence(PERSON_COMPETENCE_MIN_LEVEL, null, null);

        doReturn(Optional.of(personCompetence)).when(personCompetenceRepository).findById(any());
        doReturn(personCompetence).when(personCompetenceRepository).save(any());

        assertEquals(competenceService.downgradePersonCompetence(1L).getLevel() , PERSON_COMPETENCE_MIN_LEVEL);
    }

    @Test
    void findAllPersonCompetencesPaged(){
        PersonCompetence personCompetence = new PersonCompetence(PERSON_COMPETENCE_MIN_LEVEL, null, null);
        String[] sortBy = {"aa"};

        doReturn(new PageImpl<>(Collections.singletonList(personCompetence))).when(personCompetenceRepository).findAll((Pageable) any());

        Long personId = 1L;

        assertEquals(Collections.singletonList(personCompetence).stream()
                .map(PersonCompetenceDTO::apply)
                .collect(Collectors.toList()), competenceService.findAllPersonCompetences(personId, 0,2, sortBy));

    }


    @Test
    void findPersonCompetenceById(){
        PersonCompetence personCompetence = new PersonCompetence(PERSON_COMPETENCE_MIN_LEVEL, null, null);

        doReturn(Optional.of(personCompetence)).when(personCompetenceRepository).findById(any());

        assertEquals(competenceService.findPersonCompetenceById(1L), PersonCompetenceDTO.apply(personCompetence));

    }




}
