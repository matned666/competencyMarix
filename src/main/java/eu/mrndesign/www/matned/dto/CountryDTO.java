package eu.mrndesign.www.matned.dto;

import eu.mrndesign.www.matned.model.address.Country;

import java.util.Objects;

public class CountryDTO {

    public static CountryDTO apply(Country entity){
        return new CountryDTO(entity.getCountryName());
    }

    private String countryName;

    public CountryDTO() {
    }

    public CountryDTO(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryDTO that = (CountryDTO) o;
        return Objects.equals(countryName, that.countryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryName);
    }

    @Override
    public String toString() {
        return "CountryDTO{" +
                "countryName='" + countryName + '\'' +
                '}';
    }
}
