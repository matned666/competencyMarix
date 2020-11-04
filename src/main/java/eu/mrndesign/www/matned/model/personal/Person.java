package eu.mrndesign.www.matned.model.personal;

import eu.mrndesign.www.matned.dto.PersonDTO;
import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.audit.BaseEntity;
import eu.mrndesign.www.matned.model.address.Address;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Person extends BaseEntity implements AuditInterface {

    public static void applyDataFromDTO(Person entity, PersonDTO dto) {
        if (dto.getFirstName() != null) if (!dto.getFirstName().isEmpty()) entity.firstName = dto.getFirstName();
        if (dto.getLastName() != null) if (!dto.getLastName().isEmpty()) entity.lastName = dto.getLastName();
    }

    private String firstName;
    private String middleName;
    private String lastName;
    private String maidenName;
    private Gender gender;
    private LocalDate birthDate;

    @OneToOne
    private PersonDetails personDetails;
    @ManyToOne
    private Address address;

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PersonDetails getPersonDetails() {
        return personDetails;
    }

    public void setPersonDetails(PersonDetails personDetails) {
        this.personDetails = personDetails;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public enum Gender {
        FEMALE,
        MALE;

        public String symbol() {
            if (this == MALE)
                return "M";
            else return "F";
        }

        public String nice() {
            if (this == MALE)
                return "Male";
            else return "Female";
        }

    }
}
