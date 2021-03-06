package eu.mrndesign.www.matned.controller;

import eu.mrndesign.www.matned.dto.InventoryDTO;
import eu.mrndesign.www.matned.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/inventory")
    @ResponseBody public List<InventoryDTO> allInventory(){
        return inventoryService.findAll();
    }

    @PostMapping("/inventory")
    @ResponseBody public InventoryDTO addInventory(@RequestBody InventoryDTO inventoryDTO){
        return inventoryService.add(inventoryDTO);
    }

    @GetMapping("/inventory/{id}")
    @ResponseBody public InventoryDTO getInventoryById(@PathVariable Long id){
        return inventoryService.findById(id);
    }

    @DeleteMapping("/inventory/{id}")
    @ResponseBody public List<InventoryDTO> deleteItem(@PathVariable Long id){
        inventoryService.delete(id);
        return inventoryService.findAll();
    }

    @PostMapping("/inventory/{id}")
    @ResponseBody public InventoryDTO updateInventory(@PathVariable Long id, @RequestBody InventoryDTO inventoryDTO){
        return inventoryService.update(id, inventoryDTO);
    }
}
