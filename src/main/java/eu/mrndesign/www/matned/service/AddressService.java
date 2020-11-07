package eu.mrndesign.www.matned.service;

import eu.mrndesign.www.matned.dto.AddressDTO;
import eu.mrndesign.www.matned.dto.CityDTO;
import eu.mrndesign.www.matned.dto.CountryDTO;
import eu.mrndesign.www.matned.dto.StreetDTO;
import eu.mrndesign.www.matned.model.address.Address;
import eu.mrndesign.www.matned.model.address.City;
import eu.mrndesign.www.matned.model.address.Country;
import eu.mrndesign.www.matned.model.address.Street;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import eu.mrndesign.www.matned.repository.AddressRepository;
import eu.mrndesign.www.matned.repository.CityRepository;
import eu.mrndesign.www.matned.repository.CountryRepository;
import eu.mrndesign.www.matned.repository.StreetRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService extends BaseService<AddressDTO> {

    public static final String POST = "post";
    public static final String CITY = "city";
    public static final String STREET = "street";
    public static final String COUNTRY = "country";
    public final String ADDRESS_NOT_FOUND = "Address not found";
    public final String PROVIDE_EMPTY_DATA = "Address can't be empty";

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


    public AddressDTO findById(Long id) {
        return AddressDTO.apply(addressRepository.findById(id).orElseThrow(() -> new RuntimeException(ADDRESS_NOT_FOUND)));
    }

    public AddressDTO add(AddressDTO dto) {
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
        Address address = addressRepository.findById(id).orElseThrow(() -> new RuntimeException(ADDRESS_NOT_FOUND));
        updateAddress(address, dto);
        return AddressDTO.apply(addressRepository.save(address));
    }

    public List<AddressDTO> findAll(Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        return convertAddressEntityToDTOList(addressRepository.findAll(pageable).getContent());
    }

    public List<AddressDTO> findByStreet(String streetName, Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        List<AddressDTO> list = convertAddressEntityToDTOList(addressRepository.findByStreetName(streetName, pageable).getContent());
        return getAddressDTOsSortedByStreetRelevance(streetName, list, STREET);
    }



    public List<AddressDTO> findByPostCode(String postCode, Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        List<AddressDTO> list =  convertAddressEntityToDTOList(addressRepository.findByPostCode(postCode, pageable).getContent());
        return getAddressDTOsSortedByStreetRelevance(postCode, list, POST);
    }

    public List<AddressDTO> findByCity(String city, Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        List<AddressDTO> list =  convertAddressEntityToDTOList(addressRepository.findByCity(city, pageable).getContent());
        return getAddressDTOsSortedByStreetRelevance(city, list, CITY);
    }

    public List<AddressDTO> findByCountry(String country, Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        List<AddressDTO> list =  convertAddressEntityToDTOList(addressRepository.findByCountry(country, pageable).getContent());
        return getAddressDTOsSortedByStreetRelevance(country, list, COUNTRY);
    }

    public List<AddressDTO> delete(Long id, Integer startPage, Integer itemsPerPage, String[] sortBy) {
        addressRepository.deleteById(id);
        return findAll(startPage, itemsPerPage, sortBy);
    }

    public List<StreetDTO> findAllStreets(Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        return convertStreetEntityToDTOList(streetRepository.findAll(pageable).getContent());
    }

    public List<CityDTO> findAllCities(Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        return convertCityEntityToDTOList(cityRepository.findAll(pageable).getContent());
    }

    public List<CountryDTO> findAllCountries(Integer startPage, Integer itemsPerPage, String[] sortBy) {
        Pageable pageable = getPageable(startPage, itemsPerPage, sortBy);
        return convertCountryEntityToDTOList(countryRepository.findAll(pageable).getContent());
    }

    public List<StreetDTO> deleteStreet(Long id, Integer page, Integer amount, String[] sort) {
        streetRepository.deleteById(id);
        return findAllStreets(page, amount, sort);
    }

    public List<CityDTO> deleteCity(Long id, Integer page, Integer amount, String[] sort) {
        cityRepository.deleteById(id);
        return findAllCities(page, amount, sort);
    }

    public List<CountryDTO> deleteCountry(Long id, Integer page, Integer amount, String[] sort) {
        countryRepository.deleteById(id);
        return findAllCountries(page, amount, sort);
    }

    //    Privates

    protected boolean getSortingFilter(AddressDTO x, String searchedValue, String type) {
        switch (type){
            case STREET: return x.getStreet().equalsIgnoreCase(searchedValue);
            case POST: return x.getPostCode().equalsIgnoreCase(searchedValue);
            case CITY: return x.getCity().equalsIgnoreCase(searchedValue);
            case COUNTRY: return x.getCountry().equalsIgnoreCase(searchedValue);
        }
        return false;
    }

    private List<StreetDTO> convertStreetEntityToDTOList(List<Street> content) {
        return content.stream()
                .map(StreetDTO::apply)
                .collect(Collectors.toList());
    }

    private List<CityDTO> convertCityEntityToDTOList(List<City> content) {
        return content.stream()
                .map(CityDTO::apply)
                .collect(Collectors.toList());
    }

    private List<CountryDTO> convertCountryEntityToDTOList(List<Country> content) {
        return content.stream()
                .map(CountryDTO::apply)
                .collect(Collectors.toList());
    }

    private List<AddressDTO> convertAddressEntityToDTOList(List<Address> addresses) {
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
