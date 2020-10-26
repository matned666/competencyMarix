package eu.mrndesign.www.matned.controller;

import eu.mrndesign.www.matned.dto.InventoryDTO;
import eu.mrndesign.www.matned.model.EntityDescription;
import eu.mrndesign.www.matned.model.Inventory;
import eu.mrndesign.www.matned.model.User;
import eu.mrndesign.www.matned.service.InventoryService;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static eu.mrndesign.www.matned.JsonOps.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;


    private List<InventoryDTO> inventoryList;

    @BeforeEach
    void setup() {
        inventoryList = new LinkedList<>();
        List<Inventory> temList = new LinkedList<>();
        Inventory inventory;
        for (int i = 1; i <= 2; i++) {
            inventory = new Inventory();
            inventory.setPrice(new BigDecimal(i * 10));
            inventory.setEntityDescription(new EntityDescription(("test" + i), ("test-" + i + "-description")));
            inventory.setCreatedDate(LocalDateTime.now());
            inventory.setLastModifiedDate(LocalDateTime.now());
            inventory.setCreatedBy(new User("testCreator"));
            inventory.setLastModifiedBy(new User("testUpdater"));
            temList.add(inventory);
        }
        for (Inventory item : temList) {
            inventoryList.add(InventoryDTO.applyWithAudit(item));
        }
    }

    @AfterEach
    void reset() {
        inventoryList.clear();
    }

    @Test
    @DisplayName("GET /inventory test - inventory found 200")
    @WithMockUser(roles = "ADMIN")
    void getAllInventoryList() throws Exception {

        Mockito.doReturn(inventoryList).when(inventoryService).findAll();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/inventory")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{'name':'test1','description':'test-1-description','price':10},{'name':'test2','description':'test-2-description','price':20}]"))
                .andReturn();

    }

    @Test
    @DisplayName("GET /inventory/0 test - inventory found 200")
    @WithMockUser(roles = "ADMIN")
    void getSingleInventoryItemTest() throws Exception {

        Mockito.doReturn(inventoryList.get(0)).when(inventoryService).findById(any());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/inventory/0")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'name':'test1','description':'test-1-description','price':10}"))
                .andReturn();

    }

    @Test
    @DisplayName("DELETE /inventory/0 test - 403 - forbidden")
    @WithMockUser(roles = {"USER", "PUBLISHER"})
    void deleteItemIsOnlyAllowedForAdmins() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/inventory/0")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @DisplayName("DELETE /inventory/0 test - 200 - ok")
    @WithMockUser(roles = {"MANAGER", "USER", "PUBLISHER"})
    void deleteItemStatus200ForManagerResultsAListOfAllObjects() throws Exception {
        Mockito.doReturn(inventoryList).when(inventoryService).findAll();

        doAnswer(x -> inventoryList.remove(0))
                .when(inventoryService).delete(any());

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/inventory/0")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{'name':'test2','description':'test-2-description','price':20}]"))
                .andReturn();
    }


    @Test
    @DisplayName("GET /inventory test - status 403")
    @WithMockUser(roles = "BANNED")
    void bannedUserHasNoAccessToTheInventory() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/inventory")
                        .accept("application/json"))
                .andExpect(status().isForbidden())
                .andReturn();
    }


    @Test
    @DisplayName("POST /inventory/0 test - status 200")
    @WithMockUser(roles = {"PUBLISHER", "USER"})
    void updateItem() throws Exception {

        doReturn(inventoryList.get(0)).when(inventoryService).update(any(), any());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/inventory/0")
                        .content(asJsonString(inventoryList.get(0)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'name':'test1','description':'test-1-description','price':10}"))
                .andReturn();
    }

    @Test
    @DisplayName("POST /inventory test - status 200")
    @WithMockUser(roles = {"PUBLISHER", "USER"})
    void addItem() throws Exception {

        doReturn(inventoryList.get(0)).when(inventoryService).add(any());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/inventory")
                        .content(asJsonString(inventoryList.get(0)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'name':'test1','description':'test-1-description','price':10}"))
                .andReturn();
    }


}
