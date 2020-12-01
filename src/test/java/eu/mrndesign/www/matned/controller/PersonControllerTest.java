package eu.mrndesign.www.matned.controller;

import eu.mrndesign.www.matned.dto.PersonDTO;
import eu.mrndesign.www.matned.service.PersonService;
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
import java.util.Collections;
import java.util.List;

import static eu.mrndesign.www.matned.JsonOps.asJsonString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    private List<PersonDTO> people;

    @BeforeEach
    void setup() {
        people = new ArrayList<>();
        people.add(new PersonDTO("John", "Smith"));
        people.add(new PersonDTO("Anne", "Hathaway"));
    }

    @Test
    @DisplayName("1) get all people with status 200")
    @WithMockUser(roles = {"PUBLISHER", "USER"})
    void getAllPeopleListTest() throws Exception {
        doReturn(people).when(personService).findAll(any(), any(), any());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/person")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{'firstName':'John','lastName':'Smith'},{'firstName':'Anne','lastName':'Hathaway'}]"))
                .andReturn();
    }

    @Test
    @DisplayName("2) get all people with status 403")
    @WithMockUser(roles = "USER")
    void getAllPeopleListForbiddenForACommonUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/person"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @DisplayName("3) get single user by id with status 200")
    @WithMockUser(roles = {"PUBLISHER", "USER"})
    void getSinglePersonWithId() throws Exception {
        doReturn(people.get(1)).when(personService).findById(anyLong());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/person/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'firstName':'Anne','lastName':'Hathaway'}"))
                .andReturn();

    }

    @Test
    @DisplayName("4) get single user by id with status 403")
    @WithMockUser(roles = "USER")
    void getSinglePersonWithIdForbiddenForACommonUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/person/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andReturn();

    }

    @Test
    @DisplayName("5) get all people with status 200")
    @WithMockUser(roles = {"MANAGER", "PUBLISHER", "USER"})
    void deletePersonFromListWithAStatus200() throws Exception {
        doReturn(people).when(personService).findAll(any(), any(), any());
        doReturn(true).when(personService).delete(anyLong());

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/person/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{'firstName':'John','lastName':'Smith'},{'firstName':'Anne','lastName':'Hathaway'}]"))
                .andReturn();
    }

    @Test
    @DisplayName("6) delete person status 403 - forbidden")
    @WithMockUser(roles = {"PUBLISHER", "USER"})
    void deletePersonFromListWithAStatus403() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/person/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @DisplayName("7) get all people searched by name or last name with status 200")
    @WithMockUser(roles = {"PUBLISHER", "USER"})
    void getAllPeopleSearchedByNameList() throws Exception {
        doReturn(people).when(personService).findAll(any(), any(), any());
        doReturn(Collections.singletonList(people.get(0))).when(personService).findByFirstName(any(), any(), any(), any());
        doReturn(Collections.singletonList(people.get(1))).when(personService).findByLastName(any(), any(), any(), any());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/person?search=firstName&element=john")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{'firstName':'John','lastName':'Smith'}]"))
                .andReturn();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/person?search=lastName&element=anne")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{'firstName':'Anne','lastName':'Hathaway'}]"))
                .andReturn();
    }

    @Test
    @DisplayName("8) post a new person with status 200")
    @WithMockUser(roles = {"PUBLISHER", "USER"})
    void postSingleANewPersonTest() throws Exception {
        doReturn(people.get(1)).when(personService).add(any());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/person")
                        .content(asJsonString(people.get(1)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'firstName':'Anne','lastName':'Hathaway'}"))
                .andReturn();

    }

    @Test
    @DisplayName("9) post a new person with status 403")
    @WithMockUser(roles = "USER")
    void postSingleANewPersonTestForbidden() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/person"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @DisplayName("10) update person with status 200")
    @WithMockUser(roles = {"PUBLISHER", "USER"})
    void updateSingleAPersonTest() throws Exception {
        doReturn(people.get(1)).when(personService).update(anyLong(), any());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/person/1")
                        .content(asJsonString(people.get(1)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'firstName':'Anne','lastName':'Hathaway'}"))
                .andReturn();

    }

    @Test
    @DisplayName("11) update person with status 403")
    @WithMockUser(roles = "USER")
    void updateAPersonForbiddenTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/person/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andReturn();
    }







}
