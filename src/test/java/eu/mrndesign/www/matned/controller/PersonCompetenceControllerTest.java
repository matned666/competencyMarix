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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static eu.mrndesign.www.matned.JsonOps.asJsonString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class PersonCompetenceControllerTest {


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
    @DisplayName("GET /person/0/person_competence/0 test - forbidden for a common user")
    @WithMockUser(roles = "USER")
    void getPersonCompetenceForbidden() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/person/0/person_competence/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @DisplayName("GET /person/0/person_competence/0 test - competences found 200 with a proper role")
    @WithMockUser(roles = {"PUBLISHER","USER"})
    void getPersonCompetence() throws Exception {
        doReturn(personCompetences.get(0)).when(competenceService).findPersonCompetenceById(any());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/person/0/person_competence/0")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'level':0}"))
                .andReturn();

    }

    @Test
    @DisplayName("GET /person/0/person_competence test - competences found 200 with role")
    @WithMockUser(roles = {"PUBLISHER","USER"})
    void getAllPersonCompetencesListPage() throws Exception {
        doReturn(personCompetences).when(competenceService).findAllPersonCompetences(any(), any(), any(), any());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/person/0/person_competence")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{'level':0},{'level':1}]"))
                .andReturn();
    }

    @Test
    @DisplayName("GET /person/0/person_competence test - forbidden for a common user")
    @WithMockUser(roles = "USER")
    void getAllPersonCompetencesForbidden() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/person/0/person_competence"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @DisplayName("DELETE /person/0/person_competence/0 test - person competence deleted and all person competences shown 200 ")
    @WithMockUser(roles = {"MANAGER", "USER"})
    void deletePersonCompetence() throws Exception {
        doReturn(personCompetences).when(competenceService).findAllPersonCompetences(any(), any(), any(), any());
        doNothing().when(competenceService).deletePersonCompetence(any());

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/person/0/person_competence/0")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{'level':0},{'level':1}]"))
                .andReturn();

    }

    @Test
    @DisplayName("DELETE /person/0/person_competence/0 test - 403 ")
    @WithMockUser(roles = {"PUBLISHER", "USER"})
    void deletePersonCompetenceForbidden() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/person/0/person_competence/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andReturn();

    }

    @Test
    @DisplayName("GET /person/0/person_competence/0/up test - competences found and upgraded 200 with a proper role")
    @WithMockUser(roles = {"PUBLISHER","USER"})
    void upgradePersonCompetence() throws Exception {
        doReturn(personCompetences.get(0)).when(competenceService).upgradePersonCompetence(any());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/person/0/person_competence/0/up")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'level':0}"))
                .andReturn();
    }

    @Test
    @DisplayName("GET /person/0/person_competence/0/down test - competences found and downgraded 200 with a proper role")
    @WithMockUser(roles = {"PUBLISHER","USER"})
    void downgradePersonCompetence() throws Exception {
        doReturn(personCompetences.get(0)).when(competenceService).downgradePersonCompetence(any());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/person/0/person_competence/0/down")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'level':0}"))
                .andReturn();
    }

    @Test
    @DisplayName("GET /person/0/person_competence/0/up test -  403 with a proper role")
    @WithMockUser(roles = "USER")
    void upgradePersonCompetenceFORBIDDEN() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/person/0/person_competence/0/up"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @DisplayName("GET /person/0/person_competence/0/down test - 403 with a proper role")
    @WithMockUser(roles = "USER")
    void downgradePersonCompetenceFORBIDDEN() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/person/0/person_competence/0/down"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @DisplayName("POST /person/0/person_competence test - 200 add new")
    @WithMockUser(roles = {"PUBLISHER", "USER"})
    void addNewPersonCompetenceSuccess() throws Exception {
        doReturn(personCompetences.get(0)).when(competenceService).addPersonCompetence(any(), any(), any());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/person/0/person_competence/0")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().json("{'level':0}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("POST /person/0/person_competence test - 200 add new")
    @WithMockUser(roles = "USER")
    void addNewPersonCompetenceForbidden() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/person/0/person_competence/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andReturn();
    }



}
