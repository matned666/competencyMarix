package eu.mrndesign.www.matned.dto;

import eu.mrndesign.www.matned.dto.audit.AuditDTO;
import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.company.Position;

import javax.validation.constraints.NotEmpty;

public class PositionDTO implements DTOEintityDescriptionImplementation{

    public static PositionDTO applyWithAudit(Position entity) {
        return new PositionDTO(entity.getEntityDescription().getName(), entity.getEntityDescription().getDescription(),
                AuditInterface.apply(entity));
    }

    public static PositionDTO apply(Position entity){
        return new PositionDTO(entity.getEntityDescription().getName(), entity.getEntityDescription().getDescription());
    }

    @NotEmpty(message = "This field cannot be empty")
    private String name;
    @NotEmpty(message = "This field cannot be empty")
    private String description;
    private AuditDTO auditDTO;

    public PositionDTO() {
    }

    public PositionDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public PositionDTO(String name, String description, AuditDTO auditDTO) {
        this.name = name;
        this.description = description;
        this.auditDTO = auditDTO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PositionDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", auditDTO=" + auditDTO +
                '}';
    }
}
