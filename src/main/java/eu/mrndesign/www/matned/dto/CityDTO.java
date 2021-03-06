package eu.mrndesign.www.matned.dto;

import eu.mrndesign.www.matned.dto.audit.AuditDTO;
import eu.mrndesign.www.matned.model.address.City;
import eu.mrndesign.www.matned.model.audit.AuditInterface;

import java.util.Objects;

public class CityDTO {

    public static CityDTO applyWithAudit(City entity){
        CityDTO dto = apply(entity);
        dto.auditDTO = AuditInterface.apply(entity);
        return dto;
    }

    public static CityDTO apply(City entity){
        return new CityDTO(entity.getCityName());
    }

    private String cityName;

    private AuditDTO auditDTO;

    public CityDTO() {
    }

    public CityDTO(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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
        CityDTO cityDTO = (CityDTO) o;
        return Objects.equals(cityName, cityDTO.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityName);
    }

    @Override
    public String toString() {
        return "CityDTO{" +
                "cityName='" + cityName + '\'' +
                ", auditDTO=" + auditDTO +
                '}';
    }
}
