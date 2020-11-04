package eu.mrndesign.www.matned.model.address;

import eu.mrndesign.www.matned.dto.AddressDTO;
import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.audit.BaseEntity;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Address extends BaseEntity implements AuditInterface {

    private String number;

    @Embedded
    private EntityDescription entityDescription;

    @ManyToOne
    private Street street;

    @ManyToOne
    private City city;

    @ManyToOne
    private Country country;

    public Address() {
    }

    public Address(String number,
                   EntityDescription entityDescription,
                   Street street,
                   City city,
                   Country country) {
        this.number = number;
        this.entityDescription = entityDescription;
        this.street = street;
        this.city = city;
        this.country = country;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public EntityDescription getEntityDescription() {
        return entityDescription;
    }

    public void setEntityDescription(EntityDescription entityDescription) {
        this.entityDescription = entityDescription;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "number='" + number + '\'' +
                ", entityDescription=" + entityDescription +
                ", street=" + street +
                ", city=" + city +
                ", country=" + country +
                '}';
    }
}
