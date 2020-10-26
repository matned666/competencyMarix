package eu.mrndesign.www.matned.controller;

import eu.mrndesign.www.matned.dto.PositionDTO;
import eu.mrndesign.www.matned.service.PositionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionController {

    @Value("${default.page.size}")
    private String defaultPageSize;
    @Value("${default.page.start}")
    private String defaultPageStart;
    @Value("${default.sort.by}")
    private String defaultSortBy;

    private String[] defaultSort;

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
        defaultSort = new String[]{defaultSortBy};
    }

    @GetMapping
    public List<PositionDTO> getAllPositions(@RequestParam(defaultValue = "${default.sort.by}", name = "sort") String[] sort,
                                          @RequestParam(defaultValue = "${default.page.start}", name = "page") Integer page,
                                          @RequestParam(defaultValue = "${default.page.size}", name = "amount") Integer amount){
        return positionService.findAll(page, amount, sort);
    }

    @PostMapping
    public PositionDTO postANewPosition(@RequestBody PositionDTO position){
        return positionService.add(position);
    }

    @DeleteMapping("/{id}")
    public List<PositionDTO> deletePosition(@PathVariable Long id){
        positionService.delete(id);
        return positionService.findAll(Integer.parseInt(defaultPageSize), Integer.parseInt(defaultPageStart), defaultSort);
    }


}
