package eu.mrndesign.www.matned.controller;

import eu.mrndesign.www.matned.dto.InventoryDTO;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import eu.mrndesign.www.matned.model.company.Inventory;
import eu.mrndesign.www.matned.repository.InventoryRepository;
import eu.mrndesign.www.matned.service.InventoryService;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;

import static eu.mrndesign.www.matned.JsonOps.asJsonString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith({SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
public class InventoryControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private InventoryRepository inventoryRepository;

    private Inventory inventory;
    private InventoryDTO inventoryDTO;

    @BeforeEach
    @WithMockUser(roles = "ADMIN")
    void setup() throws Exception {
        Inventory inventoryItem = new Inventory();
        inventoryItem.setPrice(new BigDecimal(101));
        inventoryItem.setEntityDescription(new EntityDescription(("test"), ("test-description")));
        inventory = inventoryRepository.save(inventoryItem);
    }

    @AfterEach
    void reset() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/inventory/"+inventory.getId())
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void checkUserById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/inventory/"+inventory.getId())
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("price", 101).exists())
                .andReturn();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateItem() throws Exception {
        inventory.setPrice(BigDecimal.valueOf(2222));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/inventory/"+inventory.getId())
                        .content(asJsonString(InventoryDTO.apply(inventory)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("price", 2222).exists())
                .andReturn();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addItem() throws Exception {

        inventoryDTO = InventoryDTO.apply(inventory);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/inventory")
                        .content(asJsonString(inventoryDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
        String mvcResult = result.getResponse().getContentAsString();
        System.out.println("MVC RESULT >>>>>>>>>>>>>>> " + mvcResult);
        JSONObject json = new JSONObject(mvcResult);
        String idTxt = json.getJSONObject("auditDTO").getString("id");
        Long id = Long.parseLong(idTxt);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/inventory/"+id)
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();


    }


}
