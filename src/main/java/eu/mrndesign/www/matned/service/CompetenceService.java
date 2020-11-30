package eu.mrndesign.www.matned.service;

import eu.mrndesign.www.matned.dto.CompetenceDTO;
import eu.mrndesign.www.matned.dto.PersonCompetenceDTO;
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
public class CompetenceService extends BaseService<Competence> {

    public static final String COMPETENCE_NOT_FOUND = "Competence not found";
    public static final String PERSON_COMPETENCE_NOT_FOUND = "Person competence not found";
    public static final Integer PERSON_COMPETENCE_MAX_LEVEL = 3;
    public static final Integer PERSON_COMPETENCE_MIN_LEVEL = 0;
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
        if (isChanged(dto.getName())) entity.getEntityDescription().setName(dto.getName());
        if (isChanged(dto.getDescription())) entity.getEntityDescription().setDescription(dto.getDescription());
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

    public void deletePersonCompetence(Long personCompetenceId) {
        personCompetenceRepository.deleteById(personCompetenceId);
    }

    public PersonCompetenceDTO upgradePersonCompetence(Long personCompetenceId) {
        PersonCompetence personCompetence = personCompetenceRepository.findById(personCompetenceId)
                .orElseThrow(() -> new RuntimeException(PERSON_COMPETENCE_NOT_FOUND));
        if (personCompetence.getLevel() < PERSON_COMPETENCE_MAX_LEVEL)
            personCompetence.setLevel(personCompetence.getLevel() + 1);
        return PersonCompetenceDTO.apply(personCompetenceRepository.save(personCompetence));
    }

    public PersonCompetenceDTO downgradePersonCompetence(Long personCompetenceId) {
        PersonCompetence personCompetence = personCompetenceRepository.findById(personCompetenceId)
                .orElseThrow(() -> new RuntimeException(PERSON_COMPETENCE_NOT_FOUND));
        if (personCompetence.getLevel() > PERSON_COMPETENCE_MIN_LEVEL)
            personCompetence.setLevel(personCompetence.getLevel() - 1);
        return PersonCompetenceDTO.apply(personCompetenceRepository.save(personCompetence));
    }

    public List<PersonCompetenceDTO> findAllPersonCompetences(Long personId, Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        Page<PersonCompetence> competences = personCompetenceRepository.findAll(pageable);
        List<PersonCompetence> _competences = competences.getContent();
        return _competences.stream()
                .map(PersonCompetenceDTO::apply)
                .collect(Collectors.toList());
    }

    public PersonCompetenceDTO findPersonCompetenceById(Long personCompetenceId) {
        return PersonCompetenceDTO.apply(personCompetenceRepository.findById(personCompetenceId)
                .orElseThrow(() -> new RuntimeException(PERSON_COMPETENCE_NOT_FOUND)));
    }


}
