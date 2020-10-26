package eu.mrndesign.www.matned.model.address;

import eu.mrndesign.www.matned.dto.AddressDTO;
import eu.mrndesign.www.matned.model.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Address extends BaseEntity {

    @Embedded
    private Country country;
    @Embedded
    private City city;
    @Embedded
    private Building building;

    public Address() {
    }

    public Country getCountry() {
        return country;
    }

    public City getCity() {
        return city;
    }

    public Building getBuilding() {
        return building;
    }

    public static Address apply(AddressDTO addressDTO) {
        Address address = new Address();
        address.country = new Country();
        address.city = new City();
        address.building = new Building();
        address.country.setCountryName(addressDTO.getCountryName());
        address.country.setCountryCode(addressDTO.getCountryCode());
        address.city.setCityName(addressDTO.getCityName());
        address.city.setCityPostCode(addressDTO.getCityPostCode());
        address.city.setCityStreet(addressDTO.getCityStreet());
        address.city.setCityStreetNo(addressDTO.getCityStreetNo());
        address.building.setBuildingNo(addressDTO.getBuildingNo());
        address.building.setApartment(addressDTO.getApartment());
        address.building.setFloor(addressDTO.getFloor());
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        if (!super.equals(o)) return false;
        Address address = (Address) o;
        return Objects.equals(country.getCountryName(), address.country.getCountryName())
                && Objects.equals(city.getCityName(), address.city.getCityName())
                && Objects.equals(building.getBuildingNo(), address.building.getBuildingNo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), country, city, building);
    }
}
