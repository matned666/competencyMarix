package eu.mrndesign.www.matned.controller;

import eu.mrndesign.www.matned.dto.AddressDTO;
import eu.mrndesign.www.matned.dto.CityDTO;
import eu.mrndesign.www.matned.dto.CountryDTO;
import eu.mrndesign.www.matned.dto.StreetDTO;
import eu.mrndesign.www.matned.model.address.Address;
import eu.mrndesign.www.matned.model.address.City;
import eu.mrndesign.www.matned.model.address.Country;
import eu.mrndesign.www.matned.model.address.Street;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import eu.mrndesign.www.matned.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static eu.mrndesign.www.matned.JsonOps.asJsonString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;


    private List<AddressDTO> addresses;
    private List<StreetDTO> streets;
    private List<CityDTO> cities;
    private List<CountryDTO> countries;

    @BeforeEach
    void setup(){
        addresses = new LinkedList<>();
        streets = new LinkedList<>();
        cities = new LinkedList<>();
        countries = new LinkedList<>();

        for (int i = 0; i < 3; i++) {
            streets.add(StreetDTO.apply(new Street(i + (i + 1) + "-" + (i + 1) + (i + 2) + (i + 3), "TestStreetName" + (i + 1))));
        }
        for (int i = 0; i < 3; i++) {
            cities.add(CityDTO.apply(new City("City" + (i + 1))));
        }
        for (int i = 0; i < 3; i++) {
            countries.add(CountryDTO.apply(new Country("Country" + (i + 1))));
        }
        for (int i = 0; i < 3; i++) {
            addresses.add(AddressDTO.apply(new Address((i + 1) + "A",
                    new EntityDescription("Address" + (i + 1), "Description"),
                    Street.apply(streets.get(2 - i)),
                    City.apply(cities.get(i)),
                    Country.apply(countries.get(2 - i)))));
        }

    }

    @Test
    @WithMockUser(roles = {"PUBLISHER" ,"USER"})
    void getAllAddresses() throws Exception {
        Mockito.doReturn(addresses).when(addressService).findAll(any(), any(), any());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/address")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("" +
                        "[" +
                        "{'name':'Address1','description':'Description','country':'Country3','city':'City1','street':'TestStreetName3','number':'1A','postCode':'5-345'}," +
                        "{'name':'Address2','description':'Description','country':'Country2','city':'City2','street':'TestStreetName2','number':'2A','postCode':'3-234'}," +
                        "{'name':'Address3','description':'Description','country':'Country1','city':'City3','street':'TestStreetName1','number':'3A','postCode':'1-123'}" +
                        "]"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles = {"PUBLISHER" ,"USER"})
    void getAllAddressesReturnCorrectListAccordingToSearchElement() throws Exception {
        Mockito.doReturn(Collections.singletonList(addresses.get(0))).when(addressService).findByStreet(any(), any(), any(), any());
        Mockito.doReturn(Collections.singletonList(addresses.get(1))).when(addressService).findByPostCode(any(), any(), any(), any());
        Mockito.doReturn(Collections.singletonList(addresses.get(2))).when(addressService).findByCity(any(), any(), any(), any());
        Mockito.doReturn(Arrays.asList(addresses.get(0), addresses.get(1))).when(addressService).findByCountry(any(), any(), any(), any());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/address?search=street")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("" +
                        "[" +
                        "{'name':'Address1','description':'Description','country':'Country3','city':'City1','street':'TestStreetName3','number':'1A','postCode':'5-345'}" +
                        "]"))
                .andReturn();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/address?search=post")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("" +
                        "[" +
                        "{'name':'Address2','description':'Description','country':'Country2','city':'City2','street':'TestStreetName2','number':'2A','postCode':'3-234'}" +
                        "]"))
                .andReturn();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/address?search=city")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("" +
                        "[" +
                        "{'name':'Address3','description':'Description','country':'Country1','city':'City3','street':'TestStreetName1','number':'3A','postCode':'1-123'}" +
                        "]"))
                .andReturn();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/address?search=country&element=anything")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("" +
                        "[" +
                        "{'name':'Address1','description':'Description','country':'Country3','city':'City1','street':'TestStreetName3','number':'1A','postCode':'5-345'}," +
                        "{'name':'Address2','description':'Description','country':'Country2','city':'City2','street':'TestStreetName2','number':'2A','postCode':'3-234'}" +
                        "]"))
                .andReturn();


    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllAddressesFailsWhenNotAuthorized() throws Exception {
        Mockito.doReturn(addresses).when(addressService).findAll(any(), any(), any());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/address")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(roles = {"PUBLISHER" ,"USER"})
    void getAddressById() throws Exception {
        Mockito.doReturn(addresses.get(0)).when(addressService).findById(any());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/address/1")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("" +
                        "{'name':'Address1','description':'Description','country':'Country3','city':'City1','street':'TestStreetName3','number':'1A','postCode':'5-345'}"
                        ))
                .andReturn();
    }

    @Test
    @WithMockUser(roles = {"PUBLISHER" ,"USER"})
    void addAddress() throws Exception {
        Mockito.doReturn(addresses.get(0)).when(addressService).add(any());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/address")
                        .content(asJsonString(addresses.get(0)))
                        .contentType("application/json")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("" +
                        "{'name':'Address1','description':'Description','country':'Country3','city':'City1','street':'TestStreetName3','number':'1A','postCode':'5-345'}"
                ))
                .andReturn();
    }

    @Test
    @WithMockUser(roles = "USER")
    void addAddressForbiddenByNonPublisher() throws Exception {
        Mockito.doReturn(addresses.get(0)).when(addressService).add(any());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/address")
                        .content(asJsonString(addresses.get(0)))
                        .contentType("application/json")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(roles = {"MANAGER", "PUBLISHER" ,"USER"})
    void updateAddress() throws Exception {
        Mockito.doReturn(addresses.get(0)).when(addressService).update(any(), any());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/address/1")
                        .content(asJsonString(addresses.get(0)))
                        .contentType("application/json")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("" +
                        "{'name':'Address1','description':'Description','country':'Country3','city':'City1','street':'TestStreetName3','number':'1A','postCode':'5-345'}"
                ))
                .andReturn();
    }

    @Test
    @WithMockUser(roles = {"PUBLISHER" ,"USER"})
    void updateAddressForbiddenForACommonUserAndPublisher() throws Exception {
        Mockito.doReturn(addresses.get(0)).when(addressService).update(any(), any());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/address/1")
                        .content(asJsonString(addresses.get(0)))
                        .contentType("application/json")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(roles = {"CEO", "MANAGER", "PUBLISHER" ,"USER"})
    void deleteAddress() throws Exception {
        Mockito.doReturn(addresses).when(addressService).delete(any(), any(), any(), any());

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/address/1")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("" +
                        "[" +
                        "{'name':'Address1','description':'Description','country':'Country3','city':'City1','street':'TestStreetName3','number':'1A','postCode':'5-345'}," +
                        "{'name':'Address2','description':'Description','country':'Country2','city':'City2','street':'TestStreetName2','number':'2A','postCode':'3-234'}," +
                        "{'name':'Address3','description':'Description','country':'Country1','city':'City3','street':'TestStreetName1','number':'3A','postCode':'1-123'}" +
                        "]"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles = {"PUBLISHER" ,"USER"})
    void deleteAddressDenied() throws Exception {
        Mockito.doReturn(addresses).when(addressService).delete(any(), any(), any(), any());

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/address/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(roles = {"MANAGER", "PUBLISHER" ,"USER"})
    void getAllStreets() throws Exception {
        List<StreetDTO> streets = Collections.singletonList(new StreetDTO("FooStreet", "50-256"));

        Mockito.doReturn(streets).when(addressService).findAllStreets(any(), any(), any());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/address/streets")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("" +
                        "[" +
                        "{'streetName':'FooStreet','postCode':'50-256'}" +
                        "]"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles = {"PUBLISHER" ,"USER"})
    void getAllCities() throws Exception {
        List<CityDTO> cities = Collections.singletonList(new CityDTO("FooCity"));

        Mockito.doReturn(cities).when(addressService).findAllCities(any(), any(), any());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/address/cities")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("" +
                        "[" +
                        "{'cityName':'FooCity'}" +
                        "]"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles = {"PUBLISHER" ,"USER"})
    void getAllCountries() throws Exception {
        List<CountryDTO> countries = Collections.singletonList(new CountryDTO("FooCountry"));

        Mockito.doReturn(countries).when(addressService).findAllCountries(any(), any(), any());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/address/countries")
                        .accept("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("" +
                        "[" +
                        "{'countryName':'FooCountry'}" +
                        "]"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllStreetsCitiesCountriesFronDBIsForbiddenForACommonUserAndPublisher() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/address/streets"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andReturn();
        mockMvc.perform(
                MockMvcRequestBuilders.get("/address/cities"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andReturn();
        mockMvc.perform(
                MockMvcRequestBuilders.get("/address/countries"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden())
                .andReturn();

    }
}