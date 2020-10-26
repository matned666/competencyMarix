package eu.mrndesign.www.matned.service;

import eu.mrndesign.www.matned.dto.InventoryDTO;
import eu.mrndesign.www.matned.model.EntityDescription;
import eu.mrndesign.www.matned.model.Inventory;
import eu.mrndesign.www.matned.model.User;
import eu.mrndesign.www.matned.repository.InventoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static eu.mrndesign.www.matned.service.InventoryService.NO_SUCH_RECORD;
import static eu.mrndesign.www.matned.service.InventoryService.PROVIDED_EMPTY_DATA;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class})
@SpringBootTest
class InventoryServiceTest {

    @Autowired
    private InventoryService inventoryService;

    @MockBean
    private InventoryRepository inventoryRepository;


    private List<Inventory> inventoryList;

    @BeforeEach
    void setup(){
        inventoryList = new LinkedList<>();
        Inventory inventory;
        for (int i = 1; i <= 2; i++) {
            inventory = new Inventory();
            inventory.setPrice(new BigDecimal(i*10));
            inventory.setEntityDescription(new EntityDescription(("test"+i),("test-"+i+"-description")));
            inventory.setCreatedDate(LocalDateTime.now());
            inventory.setLastModifiedDate(LocalDateTime.now());
            inventory.setCreatedBy(new User("testCreator"));
            inventory.setLastModifiedBy(new User("testUpdater"));
            inventoryList.add(inventory);
        }

    }

    @AfterEach
    void reset(){
        inventoryList.clear();
    }

    @Test
    void findingAllInventoryElements(){
        doReturn(inventoryList).when(inventoryRepository).findAll();
        List<InventoryDTO> inventories = inventoryService.findAll();
        assertEquals(2, inventories.size());
    }

    @Test
    void cantFindInventoryCauseRuntimeExceptionWithMessage(){
        doReturn(null).when(inventoryRepository).findById(1L);

        assertThrows(RuntimeException.class, ()-> { inventoryService.findById(1L);}, NO_SUCH_RECORD);
    }

    @Test
    void throwsRuntimeExceptionWhenProvidedNULLInventoryDTO(){
        doReturn(inventoryList.get(0)).when(inventoryRepository).save(inventoryList.get(0));

        assertThrows(RuntimeException.class, ()-> { inventoryService.add(null);}, PROVIDED_EMPTY_DATA);
    }

    @Test
    void returnsAValidDtoWhenSavedInventoryItem(){
        doReturn(inventoryList.get(0)).when(inventoryRepository).save(any());

        InventoryDTO inventoryDTO = InventoryDTO.applyWithAudit(inventoryList.get(0));

        assertEquals(inventoryService.add(inventoryDTO), inventoryDTO);

    }

    @Test
    void dtoChangesDataInEntityObjectWhenUpdate(){
        doReturn(Optional.of(inventoryList.get(0))).when(inventoryRepository).findById(any());
        doReturn(inventoryList.get(0)).when(inventoryRepository).save(any());

        InventoryDTO inventoryDTO = InventoryDTO.applyWithAudit(inventoryList.get(1));
        inventoryService.update(0L, inventoryDTO);

        assertEquals(inventoryList.get(0).getEntityDescription().getName(), "test2");
    }

}
