package eu.mrndesign.www.matned.model.address;

import javax.persistence.Embeddable;

@Embeddable
public class Building {

    private String buildingNo;
    private String floor;
    private String apartment;

    public Building() {
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }
}
