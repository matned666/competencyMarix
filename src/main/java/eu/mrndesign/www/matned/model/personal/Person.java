package eu.mrndesign.www.matned.model.personal;

import eu.mrndesign.www.matned.dto.PersonDTO;
import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.audit.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Person extends BaseEntity implements AuditInterface {

    public static void applyDataFromDTO(Person entity, PersonDTO dto) {
        if (dto.getFirstName() != null) if (!dto.getFirstName().isEmpty()) entity.firstName = dto.getFirstName();
        if (dto.getLastName() != null) if (!dto.getLastName().isEmpty()) entity.lastName = dto.getLastName();
    }

    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
