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
class AddressControllerIntegrationTest2 {

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


    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteAddress() throws Exception {
        int addressesInDB = addressRepository.findAll().size();
        Address address = addressRepository.save(new Address());

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/address/" + address.getId()))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(addressRepository.findAll().size(), addressesInDB);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteStreet() throws Exception {
        int streetsInDB = streetRepository.findAll().size();
        Street street = streetRepository.save(new Street());

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/address/street/" + street.getId()))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(streetRepository.findAll().size(), streetsInDB);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteCity() throws Exception {
        int cities = cityRepository.findAll().size();
        City city = cityRepository.save(new City());

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/address/city/" + city.getId()))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(cityRepository.findAll().size(), cities);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteCountry() throws Exception {
        int countriesInDB = countryRepository.findAll().size();
        Country country = countryRepository.save(new Country());

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/address/country/" + country.getId()))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(countryRepository.findAll().size(), countriesInDB);
    }







}