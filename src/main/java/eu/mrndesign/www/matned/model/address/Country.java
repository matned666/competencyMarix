package eu.mrndesign.www.matned.model.address;

import javax.persistence.Embeddable;

@Embeddable
public class Country {
    private String countryName;
    private String countryCode; // ex. PL DE FR IT

    public Country() {
    }


    public String getCountryName() {
        return countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return "Country {" +
                "countryName= '" + countryName + '\'' +
                ", countryCode= '" + countryCode + '\'' +
                '}';
    }
}
