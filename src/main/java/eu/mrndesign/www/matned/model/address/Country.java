package eu.mrndesign.www.matned.model.address;

import eu.mrndesign.www.matned.dto.CountryDTO;
import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.audit.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Country extends BaseEntity implements AuditInterface {

    public static Country apply(CountryDTO dto){
        return new Country(dto.getCountryName());
    }

    private String countryName;

    public Country() {
    }

    public Country(String countryName){
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return "Country {" +
                "countryName= '" + countryName + '\'' +
                '}';
    }
}
