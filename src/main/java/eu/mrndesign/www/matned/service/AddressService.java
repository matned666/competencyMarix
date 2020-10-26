package eu.mrndesign.www.matned.service;

import eu.mrndesign.www.matned.dto.AddressDTO;
import eu.mrndesign.www.matned.model.address.Address;
import eu.mrndesign.www.matned.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final String ADDRESS_NOT_FOND = "Address not found";
    private final String PROVIDE_EMPTY_DATA = "Address can't be empty";

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }


    public AddressDTO findAddressById(Long id) {
        return AddressDTO.apply(addressRepository.findById(id).orElseThrow(() -> new RuntimeException(ADDRESS_NOT_FOND)));
    }

    public AddressDTO addAddress(AddressDTO addressDTO) {
        if (addressDTO != null) {
            Address address = Address.apply(addressDTO);
            addressRepository.save(address);
            return AddressDTO.apply(addressRepository.findById(Objects.requireNonNull(address.getId()))
                    .orElseThrow(() -> new RuntimeException(ADDRESS_NOT_FOND)));
        } else throw new RuntimeException(PROVIDE_EMPTY_DATA);

    }
}
