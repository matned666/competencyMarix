package eu.mrndesign.www.matned.dto;

import eu.mrndesign.www.matned.dto.audit.AuditDTO;
import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.personal.Person;

import javax.validation.constraints.NotEmpty;

import static eu.mrndesign.www.matned.Patterns.DATE_FORMATTER;

public class PersonDTO {

    public static PersonDTO applyWithAudit(Person entity) {
        PersonDTO dto = apply(entity);
        dto.auditDTO = AuditInterface.apply(entity);
        return dto;
    }

    public static PersonDTO apply(Person entity) {
        PersonDTO dto = new PersonDTO(entity.getFirstName(), entity.getLastName(), AuditInterface.apply(entity));
        if (entity.getBirthDate() != null) dto.dateOfBirth = entity.getBirthDate().format(DATE_FORMATTER);
        if (entity.getGender() != null)  dto.gender = entity.getGender().name();
        dto.middleName = entity.getMiddleName();
        dto.maidenName = entity.getMaidenName();
        return dto;
    }

    @NotEmpty(message = "This field cannot be empty")
    private String firstName;
    @NotEmpty(message = "This field cannot be empty")
    private String lastName;

    private String middleName;
    private String maidenName;
    private String gender;
    private String dateOfBirth;

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

    public String getMiddleName() {
        return middleName;
    }

    public String getMaidenName() {
        return maidenName;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
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
