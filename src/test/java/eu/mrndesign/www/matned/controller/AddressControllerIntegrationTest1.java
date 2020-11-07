package eu.mrndesign.www.matned.controller;

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
import eu.mrndesign.www.matned.service.AddressService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static eu.mrndesign.www.matned.JsonOps.asJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerIntegrationTest1 {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private StreetRepository streetRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CountryRepository countryRepository;

    private Street street;
    private City city;
    private Country country;
    private Address address;
    private int addressesListSize;
    private int streetsListSize;
    private int citiesListSize;
    private int countriesListSize;

    private Long addressId;
    private Long streetId;
    private Long cityId;
    private Long countryId;

    @BeforeEach
    @WithMockUser(roles = "ADMIN")
    void setup() {
        addressesListSize = addressRepository.findAll().size();
        streetsListSize = streetRepository.findAll().size();
        citiesListSize = cityRepository.findAll().size();
        countriesListSize = countryRepository.findAll().size();

        street = streetRepository.save(new Street("50-256", "ElmStreet6"));
        city = cityRepository.save(new City("Breslau666"));
        country = countryRepository.save(new Country("PolAnd666"));
        address = addressRepository.save(new Address(
                "9",
                new EntityDescription("test address", "Description for test address"),
                street,
                city,
                country));
        addressId = address.getId();
        streetId = street.getId();
        cityId = city.getId();
        countryId = country.getId();
    }

    @AfterEach
    @WithMockUser(roles = "ADMIN")
    void reset() throws Exception {
//        delete what was created - also testing delete methods
        addressRepository.deleteById(addressId);
        streetRepository.deleteById(streetId);
        cityRepository.deleteById(cityId);
        countryRepository.deleteById(countryId);
//        check if everything went back to normality
        assertEquals(addressesListSize, addressRepository.findAll().size());
        assertEquals(streetsListSize, streetRepository.findAll().size());
        assertEquals(citiesListSize, cityRepository.findAll().size());
        assertEquals(countriesListSize, countryRepository.findAll().size());
    }

    @Test
    void integrationTest() {
    }


    @Test
    @WithMockUser(roles = {"PUBLISHER", "USER"})
    void getAddress() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/address/" + address.getId())
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("" +
                        "{'name':'test address','description':'Description for test address','country':'PolAnd666','city':'Breslau666','street':'ElmStreet6','number':'9','postCode':'50-256'}"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles = {"MANAGER", "PUBLISHER", "USER"})
    void updateAddress() throws Exception {
        AddressDTO dto = AddressDTO.apply(address);
        dto.setNumber("changed number to 6");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/address/" + address.getId())
                        .content(asJsonString(dto))
                        .contentType("application/json")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("" +
                        "{'name':'test address','description':'Description for test address','country':'PolAnd666','city':'Breslau666','street':'ElmStreet6','number':'changed number to 6','postCode':'50-256'}"))
                .andReturn();
    }


}