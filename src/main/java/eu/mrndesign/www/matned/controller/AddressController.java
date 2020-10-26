package eu.mrndesign.www.matned.controller;

import eu.mrndesign.www.matned.dto.AddressDTO;
import eu.mrndesign.www.matned.service.AddressService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }


    @GetMapping("/address/{id}")
    @ResponseBody
    public AddressDTO getAddressDTO(@PathVariable Long id) {
        return addressService.findAddressById(id);
    }

    @PostMapping("/address")
    @ResponseBody
    public AddressDTO add(@RequestBody AddressDTO addressDTO) {
        return addressService.addAddress(addressDTO);
    }



}
