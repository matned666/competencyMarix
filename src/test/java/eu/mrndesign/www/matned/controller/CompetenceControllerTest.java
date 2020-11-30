package eu.mrndesign.www.matned.controller;

import eu.mrndesign.www.matned.dto.CompetenceDTO;
import eu.mrndesign.www.matned.dto.PersonCompetenceDTO;
import eu.mrndesign.www.matned.dto.PersonDTO;
import eu.mrndesign.www.matned.service.CompetenceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static eu.mrndesign.www.matned.JsonOps.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class CompetenceControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompetenceService competenceService;

    List<CompetenceDTO> competences;
    List<PersonCompetenceDTO> personCompetences;
    List<PersonDTO> people;

    @BeforeEach
    void setup() {
        competences = new ArrayList<>();
        personCompetences = new ArrayList<>();
        people = new ArrayList<>();

        for (int i = 1; i < 3; i++) {
            PersonDTO person = new PersonDTO("name"+i, "surname"+i);
            people.add(person);
            CompetenceDTO competence = new CompetenceDTO("name"+i, "description"+i);
            competences.add(competence);
            PersonCompetenceDTO personCompetence = new PersonCompetenceDTO(i-1);
            personCompetences.add(personCompetence);
        }

    }

    @AfterEach
    void reset() {
        competences.clear();
        personCompetences.clear();
        people.clear();
    }


    @Test
    @DisplayName("GET /competence test - competences found 200 with role")
    @WithMockUser(roles = "USER")
    void getAllCompetencesListPage() throws Exception {
        doReturn(competences).when(competenceService).findAll(any(), any(), any());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/competence")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{'name':'name1','description':'description1'},{'name':'name2','description':'description2'}]"))
                .andReturn();

    }

    @Test
    @DisplayName("GET /competence/x test - competence found 200 with role")
    @WithMockUser(roles = "USER")
    void getCompetenceById() throws Exception {
        doReturn(competences.get(0)).when(competenceService).findCompetenceById(any());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/competence/0")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'name':'name1','description':'description1'}"))
                .andReturn();

    }


    @Test
    @DisplayName("POST /competence test - competence result status 200")
    @WithMockUser(roles = {"PUBLISHER", "USER"})
    void addUserStatusOk() throws Exception {
        doReturn(competences.get(0)).when(competenceService).addCompetence(any());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/competence")
                        .content(asJsonString(competences.get(0)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().json("{'name':'name1','description':'description1'}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("POST /competence test - competence result status FORBIDDEN")
    @WithMockUser(roles = "USER")
    void addUserStatusForbidden() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/competence"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @DisplayName("DELETE /competence/0 test - competence deleted and all competences shown 200 ")
    @WithMockUser(roles = {"MANAGER", "USER"})
    void deleteCompetence() throws Exception {
        doReturn(competences).when(competenceService).findAll(any(), any(), any());
        doNothing().when(competenceService).deleteCompetence(any());

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/competence/0")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{'name':'name1','description':'description1'},{'name':'name2','description':'description2'}]"))
                .andReturn();

    }

@Test
    @DisplayName("DELETE /competence/0 test - competence deleted and all competences shown 200 ")
    @WithMockUser(roles = {"PUBLISHER", "USER"})
    void deleteCompetenceStatusForbidden() throws Exception {
    doNothing().when(competenceService).deleteCompetence(any());

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/competence/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andReturn();
    }


}
