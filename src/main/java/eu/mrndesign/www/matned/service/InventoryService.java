package eu.mrndesign.www.matned.service;

import eu.mrndesign.www.matned.dto.InventoryDTO;
import eu.mrndesign.www.matned.dto.PersonDTO;
import eu.mrndesign.www.matned.model.EntityDescription;
import eu.mrndesign.www.matned.model.Inventory;
import eu.mrndesign.www.matned.model.Person;
import eu.mrndesign.www.matned.repository.InventoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService extends BaseService{

    public static final String NO_SUCH_RECORD = "No such record";
    public static final String PROVIDED_EMPTY_DATA = "Provided empty data";
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<InventoryDTO> findAll() {
        return covertListToDTOList(inventoryRepository.findAll());
    }

    public List<InventoryDTO> findAll(Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        Page<Inventory> inventories = inventoryRepository.findAll(pageable);
        List<Inventory> _inventories = inventories.getContent();
        return covertListToDTOList(_inventories);
    }

    public InventoryDTO findById(Long id) {
        return InventoryDTO.applyWithAudit(inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException(NO_SUCH_RECORD)));
    }

    public InventoryDTO add(InventoryDTO dto) {
        Inventory entity = new Inventory();
        if (dto != null) {
            setInventory(entity, dto);
            return InventoryDTO.applyWithAudit(inventoryRepository.save(entity));
        } else throw new RuntimeException(PROVIDED_EMPTY_DATA);
    }

    public InventoryDTO update(Long id, InventoryDTO dto) {
        Inventory entity = inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException(NO_SUCH_RECORD));
        setInventory(entity, dto);
        return InventoryDTO.applyWithAudit(inventoryRepository.save(entity));

    }

    public void delete(Long id) {
        inventoryRepository.deleteById(id);
    }


    private void setInventory(Inventory entity, InventoryDTO dto) {
        EntityDescription entityDescription = getEntityDescription(entity, dto);
        entity.setEntityDescription(entityDescription);
        entity.setPrice(dto.getPrice());
    }


    private List<InventoryDTO> covertListToDTOList(List<Inventory> all) {
        return all.stream()
                .map(InventoryDTO::applyWithAudit)
                .collect(Collectors.toList());
    }
}
