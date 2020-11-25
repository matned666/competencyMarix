package eu.mrndesign.www.matned.service;

import eu.mrndesign.www.matned.dto.CompetenceDTO;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import eu.mrndesign.www.matned.model.personal.Competence;
import eu.mrndesign.www.matned.repository.CompetenceRepository;
import eu.mrndesign.www.matned.repository.PersonCompetenceRepository;
import org.springframework.stereotype.Service;

@Service
public class CompetenceService {

    public static final String COMPETENCE_NOT_FOUND = "Competence not found";
    private final CompetenceRepository competenceRepository;
    private final PersonCompetenceRepository personCompetenceRepository;
    private final PersonService personService;

    public CompetenceService(CompetenceRepository competenceRepository,
                             PersonCompetenceRepository personCompetenceRepository,
                             PersonService personService) {
        this.competenceRepository = competenceRepository;
        this.personCompetenceRepository = personCompetenceRepository;
        this.personService = personService;
    }

    public CompetenceDTO addCompetence(CompetenceDTO dto) {
        Competence entity = new Competence(new EntityDescription(dto.getName(), dto.getDescription()));
        return CompetenceDTO.apply(competenceRepository.save(entity));
    }

    public CompetenceDTO updateCompetence(Long id, CompetenceDTO dto) {
        Competence entity = competenceRepository.findById(id).orElseThrow(()->new RuntimeException(COMPETENCE_NOT_FOUND));
        if()
    }
}
