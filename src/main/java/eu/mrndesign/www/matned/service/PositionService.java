package eu.mrndesign.www.matned.service;

import eu.mrndesign.www.matned.dto.PositionDTO;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import eu.mrndesign.www.matned.model.company.Position;
import eu.mrndesign.www.matned.repository.PositionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionService extends BaseService {

    public static final String POSITION_NOT_FOUND = "Position not found";
    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<PositionDTO> findAll(){
        return convertEntityToDTOList(positionRepository.findAll());
    }

    public List<PositionDTO> findAll(Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        return convertEntityToDTOList(positionRepository.findAll(pageable).getContent());
    }

    public PositionDTO findById(Long id) {
        return PositionDTO.applyWithAudit(positionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(POSITION_NOT_FOUND)));
    }

    public PositionDTO add(PositionDTO dto) {
        Position position = new Position();
        setPosition(position, dto);
        return PositionDTO.applyWithAudit(positionRepository.save(position));
    }

    public PositionDTO update(Long id, PositionDTO updatedData) {
        Position positionToUpdate = positionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(POSITION_NOT_FOUND));
        positionToUpdate.getEntityDescription().setName(updatedData.getName());
        positionToUpdate.getEntityDescription().setDescription(updatedData.getDescription());
        return PositionDTO.applyWithAudit(positionRepository.save(positionToUpdate));
    }

    public void delete(Long id) {
        positionRepository.deleteById(id);
    }


//    Package private

    List<PositionDTO> convertEntityToDTOList(List<Position> content) {
        return content.stream()
                .map(PositionDTO::applyWithAudit)
                .collect(Collectors.toList());
    }


//      Private

    private void setPosition(Position entity, PositionDTO dto) {
        EntityDescription entityDescription = getEntityDescription(entity, dto);
        entity.setEntityDescription(entityDescription);
    }


}
