package eu.mrndesign.www.matned.model.personal;

import eu.mrndesign.www.matned.Patterns;
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

import static eu.mrndesign.www.matned.Patterns.DATE_FORMATTER;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Person extends BaseEntity implements AuditInterface {

    public static void applyDataFromDTO(Person entity, PersonDTO dto) {
        if (dto.getFirstName() != null) if (!dto.getFirstName().isEmpty()) entity.firstName = dto.getFirstName();
        if (dto.getLastName() != null) if (!dto.getLastName().isEmpty()) entity.lastName = dto.getLastName();
        if (dto.getMiddleName() != null) if (!dto.getMiddleName().isEmpty()) entity.middleName = dto.getMiddleName();
        if (dto.getMaidenName() != null) if (!dto.getMaidenName().isEmpty()) entity.maidenName = dto.getMaidenName();
        if (dto.getDateOfBirth() != null)
            if (Patterns.isCorrectDate(dto.getDateOfBirth(), DATE_FORMATTER) )
                entity.birthDate = LocalDate.parse(dto.getDateOfBirth(), DATE_FORMATTER);
        if (dto.getGender() != null) if (!dto.getGender().isEmpty()) entity.gender = Gender.valueOf(dto.getGender());
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMaidenName() {
        return maidenName;
    }

    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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
