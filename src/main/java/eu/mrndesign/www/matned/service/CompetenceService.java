package eu.mrndesign.www.matned.service;

import eu.mrndesign.www.matned.dto.CompetenceDTO;
import eu.mrndesign.www.matned.dto.PersonCompetenceDTO;
import eu.mrndesign.www.matned.dto.PersonDTO;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import eu.mrndesign.www.matned.model.personal.Competence;
import eu.mrndesign.www.matned.model.personal.Person;
import eu.mrndesign.www.matned.model.personal.PersonCompetence;
import eu.mrndesign.www.matned.repository.CompetenceRepository;
import eu.mrndesign.www.matned.repository.PersonCompetenceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetenceService extends BaseService<Competence>{

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
        Competence entity = getEntity(id);
        if(isChanged(dto.getName())) entity.getEntityDescription().setName(dto.getName());
        if(isChanged(dto.getDescription())) entity.getEntityDescription().setDescription(dto.getDescription());
        return CompetenceDTO.apply(competenceRepository.save(entity));
    }

    public void deleteCompetence(Long id) {
        competenceRepository.deleteById(id);
    }

    public List<CompetenceDTO> findAll(Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        Page<Competence> competences = competenceRepository.findAll(pageable);
        List<Competence> _competences = competences.getContent();
        return convertEntityToDTOList(_competences);
    }

    public CompetenceDTO findCompetenceById(Long id) {
        return CompetenceDTO.apply(getEntity(id));
    }

    public PersonCompetenceDTO addPersonCompetence(Integer level, Long personId, Long competenceId) {
        Person person = personService.findPersonByIdService(personId);
        Competence competence = getEntity(competenceId);
        return PersonCompetenceDTO.apply(personCompetenceRepository.save(new PersonCompetence(level, person, competence)));
    }

    private Competence getEntity(Long id) {
        return competenceRepository.findById(id).orElseThrow(() -> new RuntimeException(COMPETENCE_NOT_FOUND));
    }

    private List<CompetenceDTO> convertEntityToDTOList(List<Competence> competences) {
        return competences.stream()
                .map(CompetenceDTO::apply)
                .collect(Collectors.toList());
    }
}
