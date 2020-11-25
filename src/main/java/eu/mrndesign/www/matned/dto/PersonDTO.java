package eu.mrndesign.www.matned.dto;

import eu.mrndesign.www.matned.dto.audit.AuditDTO;
import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.personal.Person;

import javax.validation.constraints.NotEmpty;

import java.util.Objects;

import static eu.mrndesign.www.matned.Patterns.DATE_FORMATTER;

public class PersonDTO {

    public static PersonDTO applyWithAudit(Person entity) {
        PersonDTO dto = apply(entity);
        dto.auditDTO = AuditInterface.apply(entity);
        return dto;
    }

    public static PersonDTO apply(Person entity) {
        return new PersonDTO(entity.getFirstName(), entity.getLastName(), AuditInterface.apply(entity));
    }

    @NotEmpty(message = "This field cannot be empty")
    private String firstName;
    @NotEmpty(message = "This field cannot be empty")
    private String lastName;

    private AuditDTO auditDTO;

    public PersonDTO() {
    }

    public PersonDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PersonDTO(String firstName, String lastName, AuditDTO auditDTO) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.auditDTO = auditDTO;
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
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(firstName, personDTO.firstName) &&
                Objects.equals(lastName, personDTO.lastName) &&
                Objects.equals(auditDTO, personDTO.auditDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, auditDTO);
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", auditDTO=" + auditDTO +
                '}';
    }
}
