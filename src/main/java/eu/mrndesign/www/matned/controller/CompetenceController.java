package eu.mrndesign.www.matned.controller;

import eu.mrndesign.www.matned.dto.CompetenceDTO;
import eu.mrndesign.www.matned.service.CompetenceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompetenceController {

    private final CompetenceService competenceService;

    public CompetenceController(CompetenceService competenceService) {
        this.competenceService = competenceService;
    }

    @GetMapping("/competence")
    public List<CompetenceDTO> getAllCompetences(@RequestParam(defaultValue = "${default.sort.by}", name = "sort") String[] sort,
                                                 @RequestParam(defaultValue = "${default.page.start}", name = "page") Integer page,
                                                 @RequestParam(defaultValue = "${default.page.size}", name = "amount") Integer amount){

        return competenceService.findAll(page,amount,sort);
    }

    @GetMapping("/competence/{id}")
    public CompetenceDTO getCompetenceById(@PathVariable Long id){
        return competenceService.findCompetenceById(id);
    }

    @PostMapping("/competence")
    public CompetenceDTO addCompetence(@RequestBody CompetenceDTO competence){
        return competenceService.addCompetence(competence);
    }

    @DeleteMapping("/competence/{id}")
    public List<CompetenceDTO> getAllCompetences(@PathVariable Long id,
                                                 @RequestParam(defaultValue = "${default.sort.by}", name = "sort") String[] sort,
                                                 @RequestParam(defaultValue = "${default.page.start}", name = "page") Integer page,
                                                 @RequestParam(defaultValue = "${default.page.size}", name = "amount") Integer amount){
        competenceService.deleteCompetence(id);
        return competenceService.findAll(page,amount,sort);
    }




}
