package eu.mrndesign.www.matned.model.address;

import javax.persistence.Embeddable;

@Embeddable
public class City {

    private String cityName;
    private String cityStreet;
    private String cityStreetNo;
    private String cityPostCode;


    public City() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityStreet() {
        return cityStreet;
    }

    public void setCityStreet(String cityStreet) {
        this.cityStreet = cityStreet;
    }

    public String getCityStreetNo() {
        return cityStreetNo;
    }

    public void setCityStreetNo(String cityStreetNo) {
        this.cityStreetNo = cityStreetNo;
    }

    public String getCityPostCode() {
        return cityPostCode;
    }

    public void setCityPostCode(String cityPostCode) {
        this.cityPostCode = cityPostCode;
    }
}
