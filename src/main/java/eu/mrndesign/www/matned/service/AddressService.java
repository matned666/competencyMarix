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
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final StreetRepository streetRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    private final String ADDRESS_NOT_FOND = "Address not found";
    private final String PROVIDE_EMPTY_DATA = "Address can't be empty";

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
        return AddressDTO.apply(addressRepository.findById(id).orElseThrow(() -> new RuntimeException(ADDRESS_NOT_FOND)));
    }

    public AddressDTO addAddress(AddressDTO dto) {
        if (dto != null) {
            Street street = getStreet(dto.getStreet(), dto.getPostCode());
            City city = getCity(dto.getCity());
            Country country = getCountry(dto.getCountry());
            Address entity = new Address(
                    dto.getNumber(),
                    new EntityDescription(dto.getName(), dto.getDescription()),
                    street,
                    city,
                    country);
            addressRepository.save(entity);
            return AddressDTO.apply(addressRepository.findById(Objects.requireNonNull(entity.getId()))
                    .orElseThrow(() -> new RuntimeException(ADDRESS_NOT_FOND)));
        } else throw new RuntimeException(PROVIDE_EMPTY_DATA);
    }

    public AddressDTO update(Long id, AddressDTO dto){
        Address address = addressRepository.findById(id).orElseThrow(()->new RuntimeException("No address found"));
        updateAddress(address, dto);
        return AddressDTO.apply(addressRepository.save(address));
    }



//    Privates

private void updateAddress(Address entity, AddressDTO dto) {
    if (dto.getNumber() != null) if (!dto.getNumber().isEmpty()) entity.setNumber(dto.getNumber());
    if (dto.getName() != null) if (!dto.getName().isEmpty()) entity.getEntityDescription().setName(dto.getName());
    if (dto.getDescription() != null) if (!dto.getDescription().isEmpty()) entity.getEntityDescription().setDescription(dto.getDescription());
    if (dto.getPostCode() != null) if (!dto.getPostCode().isEmpty()) entity.getStreet().setPostCode(dto.getPostCode());
    if (dto.getStreet() != null) if (!dto.getStreet().isEmpty()) entity.getStreet().setStreetName(dto.getStreet());
    if (dto.getCity() != null) if (!dto.getCity().isEmpty()) entity.getCity().setCityName(dto.getCity());
    if (dto.getCountry() != null) if (!dto.getCountry().isEmpty()) entity.getCountry().setCountryName(dto.getCountry());
}

    private Street getStreet(String streetFromDTO, String postCodeFromDTO){
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
