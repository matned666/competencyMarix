package eu.mrndesign.www.matned.dto;

import eu.mrndesign.www.matned.model.address.Address;

import java.util.Objects;

public class AddressDTO {

    private String countryName;
    private String countryCode;
    private String cityName;
    private String cityStreet;
    private String cityStreetNo;
    private String cityPostCode;
    private String buildingNo;
    private String floor;
    private String apartment;


    private AddressDTO() {
    }

    public static AddressDTO apply(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.countryName = address.getCountry().getCountryName();
        addressDTO.countryCode = address.getCountry().getCountryCode();
        addressDTO.cityName = address.getCity().getCityName();
        addressDTO.cityStreet = address.getCity().getCityStreet();
        addressDTO.cityStreetNo = address.getCity().getCityStreetNo();
        addressDTO.cityPostCode = address.getCity().getCityPostCode();
        addressDTO.buildingNo = address.getBuilding().getBuildingNo();
        addressDTO.floor = address.getBuilding().getFloor();
        addressDTO.apartment = address.getBuilding().getApartment();

        return addressDTO;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCityStreet() {
        return cityStreet;
    }

    public String getCityStreetNo() {
        return cityStreetNo;
    }

    public String getCityPostCode() {
        return cityPostCode;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public String getFloor() {
        return floor;
    }

    public String getApartment() {
        return apartment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressDTO)) return false;
        AddressDTO that = (AddressDTO) o;
        return countryName.equals(that.countryName)
                && Objects.equals(countryCode, that.countryCode)
                && cityName.equals(that.cityName)
                && cityStreet.equals(that.cityStreet)
                && cityStreetNo.equals(that.cityStreetNo)
                && Objects.equals(cityPostCode, that.cityPostCode)
                && Objects.equals(buildingNo, that.buildingNo)
                && Objects.equals(floor, that.floor)
                && Objects.equals(apartment, that.apartment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryName, countryCode, cityName, cityStreet, cityStreetNo, cityPostCode, buildingNo, floor, apartment);
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "countryName= '" + countryName + '\'' +
                ", countryCode= '" + countryCode + '\'' +
                ", cityName= '" + cityName + '\'' +
                ", cityStreet= '" + cityStreet + '\'' +
                ", cityStreetNo= '" + cityStreetNo + '\'' +
                ", cityPostCode= '" + cityPostCode + '\'' +
                ", buildingNo= '" + buildingNo + '\'' +
                ", floor= '" + floor + '\'' +
                ", apartment= '" + apartment + '\'' +
                '}';
    }
}
