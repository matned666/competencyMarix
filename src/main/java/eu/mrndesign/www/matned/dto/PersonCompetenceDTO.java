package eu.mrndesign.www.matned.dto;

import eu.mrndesign.www.matned.dto.audit.AuditDTO;
import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.personal.PersonCompetence;

import java.util.Objects;

public class PersonCompetenceDTO {

    public static PersonCompetenceDTO applyWithAudit(PersonCompetence entity){
        PersonCompetenceDTO dto = apply(entity);
        dto.auditDTO = AuditInterface.apply(entity);
        return dto;
    }

    public static PersonCompetenceDTO apply(PersonCompetence entity){
        return new PersonCompetenceDTO(entity.getLevel());
    }

    private Integer level;

    private AuditDTO auditDTO;

    public PersonCompetenceDTO() {
    }

    public PersonCompetenceDTO(Integer level) {
        this.level = level;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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
        PersonCompetenceDTO that = (PersonCompetenceDTO) o;
        return Objects.equals(level, that.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level);
    }

    @Override
    public String toString() {
        return "PersonCompetenceDTO{" +
                "level=" + level +
                '}';
    }
}
