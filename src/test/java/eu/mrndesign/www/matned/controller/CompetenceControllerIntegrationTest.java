package eu.mrndesign.www.matned.controller;

import eu.mrndesign.www.matned.dto.CompetenceDTO;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import eu.mrndesign.www.matned.model.personal.Competence;
import eu.mrndesign.www.matned.repository.CompetenceRepository;
import eu.mrndesign.www.matned.service.CompetenceService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static eu.mrndesign.www.matned.JsonOps.asJsonString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class CompetenceControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompetenceService competenceService;

    @Autowired
    private CompetenceRepository competenceRepository;

    String name = "name1";
    String description = "desc1";

    @Test
    @WithMockUser(roles = "ADMIN")
    void addNewCompetenceWithRepository_CheckIfItExistsAndHasAProperForm_Update_AndCheckAgain_AndDeleteIt() throws Exception {
        Competence entity = competenceRepository.save(new Competence(new EntityDescription(name, description)));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/competence/" + entity.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'name':'name1','description':'desc1'}"))
                .andReturn();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/competence/" + entity.getId())
                        .content(asJsonString(new CompetenceDTO(null, "second desc")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/competence/" + entity.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'name':'name1','description':'second desc'}"))
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.delete("/competence/" + entity.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();


    }


}
