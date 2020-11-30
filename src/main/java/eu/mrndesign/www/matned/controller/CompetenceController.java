package eu.mrndesign.www.matned.controller;

import eu.mrndesign.www.matned.dto.CompetenceDTO;
import eu.mrndesign.www.matned.service.CompetenceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompetenceController {

    private final CompetenceService competenceService;

    public CompetenceController(CompetenceService competenceService) {
        this.competenceService = competenceService;
    }

//    @GetMapping("/competence")
//    public List<CompetenceDTO> getAllCompetences(){
//
//    }


}
