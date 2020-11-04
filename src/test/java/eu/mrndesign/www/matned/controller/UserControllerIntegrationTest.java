package eu.mrndesign.www.matned.controller;

import eu.mrndesign.www.matned.dto.UserDTO;
import eu.mrndesign.www.matned.dto.UserRegistrationDTO;
import eu.mrndesign.www.matned.model.personal.Person;
import eu.mrndesign.www.matned.model.security.User;
import eu.mrndesign.www.matned.model.security.UserRole;
import eu.mrndesign.www.matned.repository.PersonRepository;
import eu.mrndesign.www.matned.repository.UserRepository;
import eu.mrndesign.www.matned.repository.UserRoleRepository;
import eu.mrndesign.www.matned.service.UserService;
import org.junit.jupiter.api.*;
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
import java.util.Objects;

import static eu.mrndesign.www.matned.JsonOps.asJsonString;
import static eu.mrndesign.www.matned.service.UserService.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PersonRepository personRepository;

    private UserDTO defaultUser;
    private Person person1;
    private Person person2;
    String login = "test@test1.tst";
    String password = "test1";


    @BeforeEach
    void setup() throws Exception {
        defaultUser = new UserDTO("test@test1.tst");
        System.out.println(defaultUser);

        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO(login, password);
        userRegistrationDTO.setPasswordConfirm(password);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .content(asJsonString(userRegistrationDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @AfterEach
    @WithMockUser(roles = "ADMIN")
    void reset() throws Exception {
        User user = userRepository.findByLogin(login);

        if (user != null) {
            mockMvc.perform(
                    MockMvcRequestBuilders.delete("/users/" + user.getId())
                            .content(asJsonString(defaultUser))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept("application/json"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andReturn();

            assertThrows(RuntimeException.class, () -> userService.findUserByLogin(login), USER_NOT_FOUND);
        }
    }

    @Test
    @DisplayName("GET /users test - users found 200")
    @WithMockUser(roles = "MANAGER")
    void getAllUsersList() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(userRepository.findAll());


    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void addingUser_CheckingIfExists_deleteIt() throws Exception {

        User user = userRepository.findByLogin(login);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + user.getId())
                        .content(asJsonString(defaultUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().json("{'login':'" + login + "'}"))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    @WithMockUser(roles = "CEO")
    void addingNewUser_addingANewRole_takeTheRoleAway_DeleteUser_TEST() throws Exception {

        Long userId = userRepository.findByLogin(login).getId();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/"+userId+"/add-role/"+UserRole.Role.BANNED.name())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'login':'test@test1.tst'}"))
                .andReturn();

        List<UserRole> roles = userRoleRepository.findByUserLogin(login);
        assertTrue(roles.stream()
                .anyMatch(x -> x.getRoleName().equals(UserRole.Role.BANNED.roleName())));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/"+userId+"/take-away-role/"+UserRole.Role.BANNED.name())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

        roles = userRoleRepository.findByUserLogin(login);
        assertFalse(roles.stream()
                .anyMatch(x -> x.getRoleName().equals(UserRole.Role.BANNED.roleName())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void userRoleRepositoryQueryFindRolesByUserLoginThrowsNoException() throws Exception {
        assertDoesNotThrow(()->{userRoleRepository.findByUserLogin(login);});
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addingNewUserPersonalData() throws Exception {
//        Preparation for test
        User user = userRepository.findByLogin(login);
        person1 = personRepository.save(new Person("John", "Smith"));
        person2 = personRepository.save(new Person("Anna", "John"));

//        Add new PersonDataToUser
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/"+user.getId()+"/person/"+person1.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'login':'test@test1.tst'}"))
                .andReturn();

//        Check if the data have been applied
        assertEquals(Objects.requireNonNull(
                personRepository.findUserPersonalDataByUser(user.getId()).orElse(null)).getFirstName(),
                person1.getFirstName());


//        Update the personData of user
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/"+user.getId()+"/person/"+person2.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'login':'test@test1.tst'}"))
                .andReturn();

//        Check if data have been set
        assertEquals(Objects.requireNonNull(
                personRepository.findUserPersonalDataByUser(user.getId()).orElse(null)).getFirstName(),
                person2.getFirstName());


//        Delete the user personal data
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/"+user.getId()+"/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

//         Cleanup
        personRepository.delete(person1);
        personRepository.delete(person2);

    }





}
