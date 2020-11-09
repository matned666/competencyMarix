package eu.mrndesign.www.matned.dto;

import eu.mrndesign.www.matned.dto.audit.AuditDTO;
import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.company.Department;

import java.util.Objects;

public class DepartmentDTO {

    public static DepartmentDTO applyWithAudit(Department entity){
        DepartmentDTO dto = apply(entity);
        dto.auditDTO = AuditInterface.apply(entity);
        return dto;
    }

    public static DepartmentDTO apply(Department entity){
        return new DepartmentDTO(
                entity.getEntityDescription() != null ? entity.getEntityDescription().getName() : null,
                entity.getEntityDescription() != null ? entity.getEntityDescription().getDescription() : null
        );
    }

    private String name;
    private String description;

    private AuditDTO auditDTO;

    public DepartmentDTO() {
    }

    public DepartmentDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public AuditDTO getAuditDTO() {
        return auditDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentDTO that = (DepartmentDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "DepartmentDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", auditDTO=" + auditDTO +
                '}';
    }
}
