package eu.mrndesign.www.matned.dto;

import eu.mrndesign.www.matned.dto.audit.AuditDTO;
import eu.mrndesign.www.matned.model.address.Address;
import eu.mrndesign.www.matned.model.audit.AuditInterface;

import java.util.Objects;

public class AddressDTO {

    public static AddressDTO applyWithAudit(Address entity){
        AddressDTO addressDTO = apply(entity);
        addressDTO.auditDTO = AuditInterface.apply(entity);
        return addressDTO;

    }

    public static AddressDTO apply(Address entity) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.country = entity.getCountry().getCountryName();
        addressDTO.city = entity.getCity().getCityName();
        addressDTO.street = entity.getStreet().getStreetName();
        addressDTO.number = entity.getNumber();
        addressDTO.postCode = entity.getStreet().getPostCode();
        addressDTO.name = entity.getEntityDescription().getName();
        addressDTO.description = entity.getEntityDescription().getDescription();
        return addressDTO;
    }

    private String name;
    private String description;
    private String country;
    private String city;
    private String street;
    private String number;

    private String postCode;

    private AuditDTO auditDTO;


    private AddressDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public AuditDTO getAuditDTO() {
        return auditDTO;
    }

    public void setAuditDTO(AuditDTO auditDTO) {
        this.auditDTO = auditDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDTO that = (AddressDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(country, that.country) &&
                Objects.equals(city, that.city) &&
                Objects.equals(street, that.street) &&
                Objects.equals(number, that.number) &&
                Objects.equals(postCode, that.postCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, country, city, street, number, postCode);
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", postCode='" + postCode + '\'' +
                ", auditDTO=" + auditDTO +
                '}';
    }
}
