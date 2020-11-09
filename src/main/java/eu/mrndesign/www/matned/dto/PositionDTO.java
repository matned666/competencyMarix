package eu.mrndesign.www.matned.dto;

import eu.mrndesign.www.matned.dto.audit.AuditDTO;
import eu.mrndesign.www.matned.model.audit.AuditInterface;
import eu.mrndesign.www.matned.model.company.Position;
import eu.mrndesign.www.matned.model.company.PositionDetails;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

import static eu.mrndesign.www.matned.Patterns.DATE_FORMATTER;

public class PositionDTO implements DTOEintityDescriptionImplementation{

    public static PositionDTO applyWithAudit(Position entity) {
        PositionDTO dto = apply(entity);
        dto.auditDTO = AuditInterface.apply(entity);
        return dto;
    }

    public static PositionDTO apply(Position entity){
        return new PositionDTO(
                entity.getEntityDescription() != null ? entity.getEntityDescription().getName() : null,
                entity.getEntityDescription() != null ? entity.getEntityDescription().getDescription() : null);
    }

    public static PositionDTO applyWithAudit(Position entity, PositionDetails details) {
        PositionDTO dto = apply(entity, details);
        dto.detailsAuditDTO = AuditInterface.apply(details);
        return dto;
    }

    public static PositionDTO apply(Position entity,  PositionDetails details){
        PositionDTO dto = apply(entity);
        String creationDate = details.getPositionCreationDate() != null ? details.getPositionCreationDate().format(DATE_FORMATTER) : null;
        String deactivationDate = details.getPositionDeactivationDate() != null ? details.getPositionDeactivationDate().format(DATE_FORMATTER) : null;
        dto.positionCreationDate = creationDate;
        dto.positionDeactivationDate = deactivationDate;
        dto.isActive = details.isActive();
        return dto;    }

    @NotEmpty(message = "This field cannot be empty")
    private String name;
    @NotEmpty(message = "This field cannot be empty")
    private String description;
    private String positionCreationDate;
    private String positionDeactivationDate;
    boolean isActive;
    private AuditDTO auditDTO;
    private AuditDTO detailsAuditDTO;

    public PositionDTO() {
    }

    public PositionDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPositionCreationDate() {
        return positionCreationDate;
    }

    public String getPositionDeactivationDate() {
        return positionDeactivationDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public AuditDTO getAuditDTO() {
        return auditDTO;
    }

    public AuditDTO getDetailsAuditDTO() {
        return detailsAuditDTO;
    }

    @Override
    public String toString() {
        return "PositionDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", positionCreationDate='" + positionCreationDate + '\'' +
                ", positionDeactivationDate='" + positionDeactivationDate + '\'' +
                ", isActive=" + isActive +
                ", auditDTO=" + auditDTO +
                ", detailsAuditDTO=" + detailsAuditDTO +
                '}';
    }
}
