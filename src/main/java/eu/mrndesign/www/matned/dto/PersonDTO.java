package eu.mrndesign.www.matned.dto;

import eu.mrndesign.www.matned.model.AuditInterface;
import eu.mrndesign.www.matned.model.Person;

import javax.validation.constraints.NotEmpty;

public class PersonDTO {

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
    public String toString() {
        return "PersonDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", auditDTO=" + auditDTO +
                '}';
    }
}
