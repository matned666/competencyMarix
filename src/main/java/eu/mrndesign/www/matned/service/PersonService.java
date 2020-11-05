package eu.mrndesign.www.matned.service;

import eu.mrndesign.www.matned.dto.PersonDTO;
import eu.mrndesign.www.matned.model.personal.Person;
import eu.mrndesign.www.matned.model.address.Address;
import eu.mrndesign.www.matned.repository.AddressRepository;
import eu.mrndesign.www.matned.repository.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService extends BaseService{
    public static final String PERSON_NOT_FOUND = "Person not found";

    public static final String WRONG_DATA_GIVEN = "Null person dto object was given";
    public static final String ADDRESS_NOT_FOUND = "Address not found";

    static List<PersonDTO> convertEntityListToDTOList(List<Person> all) {
        return all.stream()
                .map(PersonDTO::apply)
                .collect(Collectors.toList());
    }

    private final AddressRepository addressRepository;

    private final PersonRepository personRepository;

    public PersonService(AddressRepository addressRepository,
                         PersonRepository personRepository) {
        this.addressRepository = addressRepository;
        this.personRepository = personRepository;
    }

    public List<PersonDTO> findAll(){
        return convertEntityListToDTOList(personRepository.findAll());
    }

    public List<PersonDTO> findAll(Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        Page<Person> persons = personRepository.findAll(pageable);
        List<Person> _persons = persons.getContent();
        return convertEntityToDTOList(_persons);
    }



    public PersonDTO findById(Long id){
        return PersonDTO.apply(findPersonByIdService(id));
    }


    public PersonDTO add(PersonDTO dto){
        return PersonDTO.apply(personRepository.save(new Person(dto.getFirstName(), dto.getLastName())));
    }

    public PersonDTO updateAddress(Long personId, Long addressId){
        Person person = personRepository.findById(personId)
                .orElseThrow(()->new RuntimeException(PERSON_NOT_FOUND));
        Address address = addressRepository.findById(addressId)
                .orElseThrow(()->new RuntimeException(ADDRESS_NOT_FOUND));
        person.setAddress(address);
        return PersonDTO.apply(personRepository.save(person));
    }

    public PersonDTO update(Long id, PersonDTO dto){
        if(dto != null) {
            Person person = findPersonByIdService(id);
            if (dto.getFirstName() != null) if (!dto.getFirstName().isEmpty())
                person.setFirstName(dto.getFirstName());
            if (dto.getLastName() != null) if (!dto.getLastName().isEmpty())
                person.setLastName(dto.getLastName());
            return PersonDTO.apply(personRepository.save(person));
        }
        throw new RuntimeException(WRONG_DATA_GIVEN);
    }

    public boolean delete(Long id){
        personRepository.deletePersonById(id);
        return personRepository.findById(id).orElse(null) == null;
    }

    public PersonDTO saveUserPersonalData(Long userId, PersonDTO personDTO) {
        Person person = personRepository.findUserPersonalDataByUser(userId).orElse(new Person());
        Person.applyDataFromDTO(person, personDTO);
        return PersonDTO.apply(personRepository.save(person));
    }

    public List<PersonDTO> findByFirstName(String firstName, Integer startPage, Integer itemsPerPage, String[] sortBy){
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        return convertEntityListToDTOList(personRepository.findByFirstNameNotPrecise(firstName, pageable).getContent());
    }

    public List<PersonDTO> findByLastName(String lastName, Integer startPage, Integer itemsPerPage, String[] sortBy){
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        return convertEntityListToDTOList(personRepository.findByLastNameNotPrecise(lastName, pageable).getContent());
    }

    public List<PersonDTO> findByAddress(Long addressId, Integer startPage, Integer itemsPerPage, String[] sortBy){
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        return convertEntityListToDTOList(personRepository.findByAddressId(addressId, pageable).getContent());
    }

//    Package private

    Person findPersonByIdService(Long id){
        return personRepository.findById(id).orElseThrow(()->new RuntimeException(PERSON_NOT_FOUND));
    }

    List<PersonDTO> convertEntityToDTOList(List<Person> all){
        return all.stream()
                .map(PersonDTO::apply)
                .collect(Collectors.toList());
    }


}
