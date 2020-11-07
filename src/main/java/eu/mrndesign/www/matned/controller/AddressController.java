package eu.mrndesign.www.matned.controller;

import eu.mrndesign.www.matned.dto.AddressDTO;
import eu.mrndesign.www.matned.dto.CityDTO;
import eu.mrndesign.www.matned.dto.CountryDTO;
import eu.mrndesign.www.matned.dto.StreetDTO;
import eu.mrndesign.www.matned.service.AddressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static eu.mrndesign.www.matned.service.AddressService.*;

@RestController

public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/address")
    @ResponseBody
    public List<AddressDTO> getAllAddresses(@RequestParam(defaultValue = "${default.sort.by}", name = "sort") String[] sort,
                                            @RequestParam(defaultValue = "${default.page.start}", name = "page") Integer page,
                                            @RequestParam(defaultValue = "${default.page.size}", name = "amount") Integer amount,
                                            @RequestParam(defaultValue = "all", name = "search") String search,
                                            @RequestParam(defaultValue = "", name = "element") String element
    ) {
        switch (search){
            case STREET: return addressService.findByStreet(element, page, amount, sort);
            case POST: return addressService.findByPostCode(element, page, amount, sort);
            case CITY: return addressService.findByCity(element, page, amount, sort);
            case COUNTRY: return addressService.findByCountry(element, page, amount, sort);
            default: return addressService.findAll(page, amount, sort);

        }
    }

    @GetMapping("/address/{id}")
    @ResponseBody
    public AddressDTO getAddressById(@PathVariable Long id) {
        return addressService.findById(id);
    }

    @PostMapping("/address")
    @ResponseBody
    public AddressDTO addAddress(@RequestBody AddressDTO addressDTO) {
        return addressService.add(addressDTO);
    }

    @PostMapping("/address/{id}")
    @ResponseBody
    public AddressDTO updateAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        return addressService.update(id, addressDTO);
    }

    @DeleteMapping("/address/{id}")
    @ResponseBody
    public List<AddressDTO> deleteAddress(@PathVariable Long id,
                                          @RequestParam(defaultValue = "${default.sort.by}", name = "sort") String[] sort,
                                          @RequestParam(defaultValue = "${default.page.start}", name = "page") Integer page,
                                          @RequestParam(defaultValue = "${default.page.size}", name = "amount") Integer amount){
        return addressService.delete(id, page, amount, sort);
    }

    @GetMapping("/address/streets")
    @ResponseBody
    public List<StreetDTO> getAllStreets(@RequestParam(defaultValue = "${default.sort.by}", name = "sort") String[] sort,
                                         @RequestParam(defaultValue = "${default.page.start}", name = "page") Integer page,
                                         @RequestParam(defaultValue = "${default.page.size}", name = "amount") Integer amount){
        return addressService.findAllStreets(page, amount, sort);
    }

    @GetMapping("/address/cities")
    @ResponseBody
    public List<CityDTO> getAllCities(@RequestParam(defaultValue = "${default.sort.by}", name = "sort") String[] sort,
                                      @RequestParam(defaultValue = "${default.page.start}", name = "page") Integer page,
                                      @RequestParam(defaultValue = "${default.page.size}", name = "amount") Integer amount){
        return addressService.findAllCities(page, amount, sort);
    }

    @GetMapping("/address/countries")
    @ResponseBody
    public List<CountryDTO> getAllCountries(@RequestParam(defaultValue = "${default.sort.by}", name = "sort") String[] sort,
                                            @RequestParam(defaultValue = "${default.page.start}", name = "page") Integer page,
                                            @RequestParam(defaultValue = "${default.page.size}", name = "amount") Integer amount){
        return addressService.findAllCountries(page, amount, sort);
    }




}
