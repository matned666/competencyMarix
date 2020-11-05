package eu.mrndesign.www.matned.dto;

import eu.mrndesign.www.matned.model.address.Address;

import java.util.Objects;

public class AddressDTO {

    private String name;
    private String description;
    private String country;
    private String city;
    private String street;
    private String number;
    private String postCode;


    private AddressDTO() {
    }

    public static AddressDTO apply(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.country = address.getCountry().getCountryName();
        addressDTO.city = address.getCity().getCityName();
        addressDTO.street = address.getStreet().getStreetName();
        addressDTO.number = address.getNumber();
        addressDTO.postCode = address.getStreet().getPostCode();
        addressDTO.name = address.getEntityDescription().getName();
        addressDTO.description = address.getEntityDescription().getDescription();
        return addressDTO;
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
                '}';
    }
}
