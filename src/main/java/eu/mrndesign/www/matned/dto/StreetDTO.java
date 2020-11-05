package eu.mrndesign.www.matned.dto;

import eu.mrndesign.www.matned.model.address.Street;

import java.util.Objects;

public class StreetDTO {

    public static StreetDTO apply(Street entity){
        return new StreetDTO(entity.getStreetName(), entity.getPostCode());
    }

    private String streetName;
    private String postCode;

    public StreetDTO() {
    }

    public StreetDTO(String streetName, String postCode) {
        this.streetName = streetName;
        this.postCode = postCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
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
        StreetDTO streetDTO = (StreetDTO) o;
        return Objects.equals(streetName, streetDTO.streetName) &&
                Objects.equals(postCode, streetDTO.postCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetName, postCode);
    }

    @Override
    public String toString() {
        return "StreetDTO{" +
                "streetName='" + streetName + '\'' +
                ", postCode='" + postCode + '\'' +
                '}';
    }
}
