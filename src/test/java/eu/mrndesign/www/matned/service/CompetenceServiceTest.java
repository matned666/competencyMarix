package eu.mrndesign.www.matned.service;

import eu.mrndesign.www.matned.dto.CompetenceDTO;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import eu.mrndesign.www.matned.model.personal.Competence;
import eu.mrndesign.www.matned.repository.CompetenceRepository;
import eu.mrndesign.www.matned.repository.PersonCompetenceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        doReturn(competence).when(competenceRepository).findById(any());


        CompetenceDTO competenceDTO = new CompetenceDTO("name", "description");
        doReturn(competence).when(competenceRepository).save(any());

        assertEquals(competenceService.updateCompetence(0L, competenceDTO), competenceDTO);
    }

    @Test
    void successfulDeleteOfCompetence(){

    }

    @Test
    void successfulShowAllCompetencesAsDTO(){

    }

    @Test
    void successfulShowCompetenceById(){

    }

    @Test
    void successfulAddPersonCompetence(){

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
