package eu.mrndesign.www.matned.controller;

import eu.mrndesign.www.matned.dto.PersonCompetenceDTO;
import eu.mrndesign.www.matned.service.CompetenceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class PersonCompetenceController {

    private final CompetenceService competenceService;

    public PersonCompetenceController(CompetenceService competenceService) {
        this.competenceService = competenceService;
    }

    @GetMapping("/person/{personId}/person_competence/{competenceId}")
    public PersonCompetenceDTO getCompetence(@PathVariable Long personId,
                                             @PathVariable Long competenceId){
        return competenceService.findPersonCompetenceById(competenceId);
    }

    @GetMapping("/person/{personId}/person_competence")
    public List<PersonCompetenceDTO> getAllPersonCompetences(@PathVariable Long personId,
                                                             @RequestParam(defaultValue = "${default.sort.by}", name = "sort") String[] sort,
                                                             @RequestParam(defaultValue = "${default.page.start}", name = "page") Integer page,
                                                             @RequestParam(defaultValue = "${default.page.size}", name = "amount") Integer amount){
        return competenceService.findAllPersonCompetences(personId, page, amount, sort);
    }

    @PostMapping("/person/{personId}/person_competence/{competenceId}")
    public PersonCompetenceDTO addNewPersonCompetence(@PathVariable Long personId,
                                                      @PathVariable Long competenceId,
                                                      @RequestParam(defaultValue = "0", name = "level") Integer level){
        return competenceService.addPersonCompetence(level, personId, competenceId);
    }

    @GetMapping("/person/{personId}/person_competence/{competenceId}/up")
    public PersonCompetenceDTO upgradePersonCompetence(@PathVariable Long competenceId,
                                                       @PathVariable Long personId){
        return competenceService.upgradePersonCompetence(competenceId);
    }

    @GetMapping("/person/{personId}/person_competence/{competenceId}/down")
    public PersonCompetenceDTO downgradePersonCompetence(@PathVariable Long competenceId,
                                                       @PathVariable Long personId){
        return competenceService.downgradePersonCompetence(competenceId);
    }

    @DeleteMapping("/person/{personId}/person_competence/{pCompetenceId}")
    public List<PersonCompetenceDTO> deletePersonCompetence(@PathVariable Long personId,
                                                            @PathVariable Long pCompetenceId,
                                                            @RequestParam(defaultValue = "${default.sort.by}", name = "sort") String[] sort,
                                                            @RequestParam(defaultValue = "${default.page.start}", name = "page") Integer page,
                                                            @RequestParam(defaultValue = "${default.page.size}", name = "amount") Integer amount){
        competenceService.deletePersonCompetence(pCompetenceId);
        return competenceService.findAllPersonCompetences(personId, page, amount, sort);
    }



}
