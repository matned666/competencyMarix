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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static eu.mrndesign.www.matned.service.PersonService.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class})
@SpringBootTest
class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @MockBean
    private AddressRepository addressRepository;
    @MockBean
    private StreetRepository streetRepository;
    @MockBean
    private CityRepository cityRepository;
    @MockBean
    private CountryRepository countryRepository;


    private List<AddressDTO> addressesDTOs;

    private List<Address> addresses;
    private List<Street> streets;
    private List<City> cities;
    private List<Country> countries;

    @BeforeEach
    void setup() {
        addresses = new LinkedList<>();
        addressesDTOs = new LinkedList<>();
        streets = new LinkedList<>();
        cities = new LinkedList<>();
        countries = new LinkedList<>();

        for (int i = 0; i < 3; i++) {
            streets.add(new Street(i + (i + 1) + "-" + (i + 1) + (i + 2) + (i + 3), "TestStreetName" + (i + 1)));
        }
        for (int i = 0; i < 3; i++) {
            cities.add(new City("City" + (i + 1)));
        }
        for (int i = 0; i < 3; i++) {
            countries.add(new Country("Country" + (i + 1)));
        }
        for (int i = 0; i < 3; i++) {
            addresses.add(new Address((i + 1) + "A",
                    new EntityDescription("Address" + (i + 1), "Description"),
                    streets.get(2 - i),
                    cities.get(i),
                    countries.get(2 - i)));
        }
        addressesDTOs = addresses.stream()
                .map(AddressDTO::apply)
                .collect(Collectors.toList());
    }


    @Test
    void findAddressById() {
        doReturn(Optional.of(addresses.get(0))).when(addressRepository).findById(any());

        assertEquals(addressService.findAddressById(1L), addressesDTOs.get(0));
    }

    @Test
    void findAddressByIdNotFoundThrowsRuntimeException() {
        doReturn(null).when(addressRepository).findById(any());

        assertThrows(RuntimeException.class, () -> addressService.findAddressById(1L), ADDRESS_NOT_FOUND);
    }

    @Test
    void addAddress() {
        doReturn(addresses.get(0)).when(addressRepository).save(any());

        assertEquals(addressService.addAddress(addressesDTOs.get(0)), addressesDTOs.get(0));
    }

    @Test
    void addAddressThrowsRuntimeExceptionWhenEmptyAddressProvided() {
        assertThrows(RuntimeException.class, ()->addressService.addAddress(null), addressService.PROVIDE_EMPTY_DATA);
    }

    @Test
    void updateWorks() {
        doReturn(Optional.of(addresses.get(0))).when(addressRepository).findById(any());
        doReturn(addresses.get(0)).when(addressRepository).save(any());

        assertEquals(addressService.update(1L, addressesDTOs.get(0)), addressesDTOs.get(0));
    }

    @Test
    void updateUpdates() {
        doReturn(Optional.of(addresses.get(0))).when(addressRepository).findById(any());
        doReturn(addresses.get(0)).when(addressRepository).save(any());

        assertEquals(addresses.get(0).getStreet().getStreetName(), streets.get(2).getStreetName());

        addressService.update(1L, addressesDTOs.get(1));

        assertEquals(addresses.get(0).getStreet().getStreetName(), streets.get(1).getStreetName());
    }

    @Test
    void update_fieldNotUpdatedWhenWhitespaceCharactersOnlyOrIsNull() {
        addressesDTOs.get(1).setCountry(null);
        addressesDTOs.get(1).setCity("    ");

        doReturn(Optional.of(addresses.get(0))).when(addressRepository).findById(any());
        doReturn(addresses.get(0)).when(addressRepository).save(any());


        assertEquals(addresses.get(0).getStreet().getStreetName(), streets.get(2).getStreetName());
        assertEquals(addresses.get(0).getCity().getCityName(), cities.get(0).getCityName());
        assertEquals(addresses.get(0).getCountry().getCountryName(), countries.get(2).getCountryName());

        addressService.update(1L, addressesDTOs.get(1));

        assertEquals(addresses.get(0).getStreet().getStreetName(), streets.get(1).getStreetName());
        assertEquals(addresses.get(0).getCity().getCityName(), cities.get(0).getCityName());
        assertEquals(addresses.get(0).getCountry().getCountryName(), countries.get(2).getCountryName());
    }

    @Test
    void updateThtowsRuntimeExceptionWhenNoUserFound() {
        doReturn(null).when(addressRepository).findById(any());

        assertThrows(RuntimeException.class, () -> addressService.findAddressById(1L), ADDRESS_NOT_FOUND);
    }

    @Test
    void findAll() {
        Pageable pageable = addressService.getPageable(1,1,new String[]{"anything"});

        doReturn(new PageImpl<>(addresses.subList(0, 3), pageable, addresses.size())).when(addressRepository).findAll((Pageable) any());

        assertEquals(addressService.findAll(1,1,new String[]{"anything"}).get(0), addressesDTOs.get(0));
        assertEquals(addressService.findAll(1,1,new String[]{"anything"}).get(1), addressesDTOs.get(1));
        assertEquals(addressService.findAll(1,1,new String[]{"anything"}).get(2), addressesDTOs.get(2));
    }

    @Test
    void findByStreet() {
        Pageable pageable = addressService.getPageable(1,1,new String[]{"anything"});
        doReturn(new PageImpl<>(addresses.subList(0, 3), pageable, addresses.size())).when(addressRepository).findByStreetName(any(), any());

        assertEquals(addressService.findByStreet("country", 1,2,new String[]{"anything"}), addressesDTOs);
    }

    @Test
    void findByPostCode() {
        Pageable pageable = addressService.getPageable(1,1,new String[]{"anything"});
        doReturn(new PageImpl<>(addresses.subList(0, 3), pageable, addresses.size())).when(addressRepository).findByPostCode(any(), any());

        assertEquals(addressService.findByPostCode("country", 1,2,new String[]{"anything"}), addressesDTOs);
    }

    @Test
    void findByCity() {
        Pageable pageable = addressService.getPageable(1,1,new String[]{"anything"});
        doReturn(new PageImpl<>(addresses.subList(0, 3), pageable, addresses.size())).when(addressRepository).findByCity(any(), any());

        assertEquals(addressService.findByCity("country", 1,2,new String[]{"anything"}), addressesDTOs);
    }

    @Test
    void findByCountry() {
        Pageable pageable = addressService.getPageable(1,1,new String[]{"anything"});
        doReturn(new PageImpl<>(addresses.subList(0, 3), pageable, addresses.size())).when(addressRepository).findByCountry(any(), any());

        assertEquals(addressService.findByCountry("country", 1,2,new String[]{"anything"}), addressesDTOs);
    }

    @Test
    void delete() {
        Pageable pageable = addressService.getPageable(1,1,new String[]{"anything"});

        doReturn(new PageImpl<>(addresses.subList(0, 3), pageable, addresses.size())).when(addressRepository).findAll((Pageable) any());

        assertEquals(addressService.delete(1L).get(0), addressesDTOs.get(0));
        assertEquals(addressService.delete(1L).get(1), addressesDTOs.get(1));
        assertEquals(addressService.delete(1L).get(2), addressesDTOs.get(2));
    }

    @Test
    void findAllStreets(){
        Pageable pageable = addressService.getPageable(1,1,new String[]{"anything"});

        doReturn(new PageImpl<>(streets.subList(0, 3), pageable, streets.size())).when(streetRepository).findAll((Pageable) any());

        assertEquals(addressService.findAllStreets(0,1,new String[]{}).get(0), StreetDTO.apply(streets.get(0)));
        assertEquals(addressService.findAllStreets(0,1,new String[]{}).get(1), StreetDTO.apply(streets.get(1)));
        assertEquals(addressService.findAllStreets(0,1,new String[]{}).get(2), StreetDTO.apply(streets.get(2)));
    }

    @Test
    void findAllCities(){
        Pageable pageable = addressService.getPageable(1,1,new String[]{"anything"});

        doReturn(new PageImpl<>(cities.subList(0, 3), pageable, cities.size())).when(cityRepository).findAll((Pageable) any());

        assertEquals(addressService.findAllCities(0,1,new String[]{}).get(0), CityDTO.apply(cities.get(0)));
        assertEquals(addressService.findAllCities(0,1,new String[]{}).get(1), CityDTO.apply(cities.get(1)));
        assertEquals(addressService.findAllCities(0,1,new String[]{}).get(2), CityDTO.apply(cities.get(2)));
    }

    @Test
    void findAllCountries(){
        Pageable pageable = addressService.getPageable(1,1,new String[]{"anything"});

        doReturn(new PageImpl<>(countries.subList(0, 3), pageable, countries.size())).when(countryRepository).findAll((Pageable) any());

        assertEquals(addressService.findAllCountries(0,1,new String[]{}).get(0), CountryDTO.apply(countries.get(0)));
        assertEquals(addressService.findAllCountries(0,1,new String[]{}).get(1), CountryDTO.apply(countries.get(1)));
        assertEquals(addressService.findAllCountries(0,1,new String[]{}).get(2), CountryDTO.apply(countries.get(2)));
    }



}
