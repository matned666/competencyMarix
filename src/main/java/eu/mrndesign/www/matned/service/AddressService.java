package eu.mrndesign.www.matned.service;

import eu.mrndesign.www.matned.dto.AddressDTO;
import eu.mrndesign.www.matned.model.address.Address;
import eu.mrndesign.www.matned.model.address.City;
import eu.mrndesign.www.matned.model.address.Country;
import eu.mrndesign.www.matned.model.address.Street;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import eu.mrndesign.www.matned.repository.AddressRepository;
import eu.mrndesign.www.matned.repository.CityRepository;
import eu.mrndesign.www.matned.repository.CountryRepository;
import eu.mrndesign.www.matned.repository.StreetRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService extends BaseService {

    public final String ADDRESS_NOT_FOUND = "Address not found";
    public final String PROVIDE_EMPTY_DATA = "Address can't be empty";

    @Value("${default.sort.by}") private String defaultSortBy;
    @Value("${default.page.start}") private Integer defaultStartPage;
    @Value("${default.page.size}") private Integer defaultPageSize;

    private final AddressRepository addressRepository;
    private final StreetRepository streetRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    public AddressService(AddressRepository addressRepository,
                          StreetRepository streetRepository,
                          CityRepository cityRepository,
                          CountryRepository countryRepository) {
        this.addressRepository = addressRepository;
        this.streetRepository = streetRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }


    public AddressDTO findAddressById(Long id) {
        return AddressDTO.apply(addressRepository.findById(id).orElseThrow(() -> new RuntimeException(ADDRESS_NOT_FOUND)));
    }

    public AddressDTO addAddress(AddressDTO dto) {
        if (dto != null) {
            Address entity = new Address(
                    dto.getNumber(),
                    new EntityDescription(dto.getName(), dto.getDescription()),
                    getStreet(dto.getStreet(), dto.getPostCode()),
                    getCity(dto.getCity()),
                    getCountry(dto.getCountry()));
            return AddressDTO.apply(addressRepository.save(entity));
        } else throw new RuntimeException(PROVIDE_EMPTY_DATA);
    }

    public AddressDTO update(Long id, AddressDTO dto) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new RuntimeException("No address found"));
        updateAddress(address, dto);
        return AddressDTO.apply(addressRepository.save(address));
    }

    public List<AddressDTO> findAll(Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        return convertEntityToDTOList(addressRepository.findAll(pageable).getContent());
    }

    public List<AddressDTO> findByStreet(String streetName, Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        return convertEntityToDTOList(addressRepository.findByStreetName(streetName, pageable).getContent());
    }

    public List<AddressDTO> findByPostCode(String postCode, Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        return convertEntityToDTOList(addressRepository.findByPostCode(postCode, pageable).getContent());
    }

    public List<AddressDTO> findByCity(String city, Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        return convertEntityToDTOList(addressRepository.findByCity(city, pageable).getContent());
    }

    public List<AddressDTO> findByCountry(String country, Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        return convertEntityToDTOList(addressRepository.findByCountry(country, pageable).getContent());
    }

    public List<AddressDTO> delete(Long id){
        addressRepository.deleteById(id);
        return findAll(defaultStartPage, defaultPageSize, new String[]{defaultSortBy});
    }





//    Privates

    private List<AddressDTO> convertEntityToDTOList(List<Address> addresses) {
        return addresses.stream()
                .map(AddressDTO::apply)
                .collect(Collectors.toList());
    }

    private void updateAddress(Address entity, AddressDTO dto) {
        if (dto.getNumber() != null)
            if (!dto.getNumber().trim().isEmpty())
                entity.setNumber(dto.getNumber());
        if (dto.getName() != null)
            if (!dto.getName().trim().isEmpty())
                entity.getEntityDescription().setName(dto.getName());
        if (dto.getDescription() != null)
            if (!dto.getDescription().trim().isEmpty())
                entity.getEntityDescription().setDescription(dto.getDescription());
        if (dto.getPostCode() != null)
            if (!dto.getPostCode().trim().isEmpty())
                entity.getStreet().setPostCode(dto.getPostCode());
        if (dto.getStreet() != null)
            if (!dto.getStreet().trim().isEmpty())
                entity.getStreet().setStreetName(dto.getStreet());
        if (dto.getCity() != null)
            if (!dto.getCity().trim().isEmpty())
                entity.getCity().setCityName(dto.getCity());
        if (dto.getCountry() != null)
            if (!dto.getCountry().trim().isEmpty())
                entity.getCountry().setCountryName(dto.getCountry());
    }

    private Street getStreet(String streetFromDTO, String postCodeFromDTO) {
        return streetRepository.findByStreetNameAdnPostCode(streetFromDTO, postCodeFromDTO)
                .orElse(streetRepository.save(new Street(streetFromDTO, postCodeFromDTO)));
    }

    private City getCity(String cityFromDTO) {
        return cityRepository.findByCityName(cityFromDTO)
                .orElse(cityRepository.save(new City(cityFromDTO)));
    }

    private Country getCountry(String countryFromDTO) {
        return countryRepository.findCountryByName(countryFromDTO)
                .orElse(countryRepository.save(new Country(countryFromDTO)));
    }
}
